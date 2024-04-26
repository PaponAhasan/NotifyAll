package com.example.notifyall.screens.viemodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyall.fcm.NotificationBody
import com.example.notifyall.fcm.SendMessageDto
import com.example.notifyall.repository.NotificationRepository
import com.example.notifyall.screens.healper.FieldsState
import com.example.notifyall.screens.healper.Validator
import com.example.notifyall.screens.pages.home_screen.HomeState
import com.example.notifyall.screens.pages.register_screen.RegisterState
import com.example.notifyall.util.Constants.NOTIFICATION_TOPIC
import com.example.notifyall.util.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    var notificationFieldState = mutableStateOf(FieldsState())

    private val _notificationState = Channel<HomeState>()
    val notificationState = _notificationState.receiveAsFlow()

    /*init {
        viewModelScope.launch {
            Firebase.messaging.subscribeToTopic("/topics/myTopic").await()
            val localToken = Firebase.messaging.token.await()
            Log.d("TAG", "subscribeToTopic: $localToken")
        }
    }*/

    fun subscribeToTopic() = viewModelScope.launch {
        Firebase.messaging.subscribeToTopic(NOTIFICATION_TOPIC).await()
        val localToken = Firebase.messaging.token.await()
        Log.d("TAG", "subscribeToTopic: $localToken")
    }

    fun unsubscribeToTopic() = viewModelScope.launch {
        Firebase.messaging.unsubscribeFromTopic(NOTIFICATION_TOPIC).await()
        val localToken = Firebase.messaging.token.await()
        Log.d("TAG", "subscribeToTopic: $localToken")
    }

    fun sendNotification(notificationBody: NotificationBody) = viewModelScope.launch {
        val messageDto = SendMessageDto(
            to = NOTIFICATION_TOPIC,
            data = notificationBody
        )
        notificationRepository.sendNotification(messageDto).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _notificationState.send(HomeState(isSuccess = result.message))
                }
                is Resource.Loading -> {
                    _notificationState.send(HomeState(isLoading = true))
                }
                is Resource.Error -> {
                    _notificationState.send(HomeState(isError = result.message))
                }
            }
        }
    }
    fun validateInputField(): Pair<Boolean, String> {
        val notificationTitle = Validator.validateNotificationTitle(
            notificationFieldState.value.notification
        )
        //Log.d("TAG", "validateInputField: $notificationTitle")
        // Set error state
        notificationFieldState.value = notificationFieldState.value.copy(
            notificationError = notificationTitle.status,
        )
        // get error message
        if (!notificationTitle.status) {
            return Pair(false, notificationTitle.message)
        }
        return Pair(true, "")
    }
}