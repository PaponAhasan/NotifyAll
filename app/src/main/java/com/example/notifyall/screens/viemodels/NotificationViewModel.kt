package com.example.notifyall.screens.viemodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notifyall.models.Notification
import com.example.notifyall.screens.healper.FieldsState
import com.example.notifyall.screens.healper.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    var notificationFieldState = mutableStateOf(FieldsState())

    fun sendNotification(notification: Notification) {
        Log.d("TAG", "sendNotification: ")
    }
    fun validateInputField(): Pair<Boolean, String> {
        val notificationTitle = Validator.validateNotificationTitle(
            notificationFieldState.value.notification
        )
        Log.d("TAG", "validateInputField: $notificationTitle")
        // Set error state
        notificationFieldState.value = notificationFieldState.value.copy(
            notificationError = notificationTitle.status,
        )
        // get error message
        if (!notificationTitle.status) {
            return Pair(false, notificationTitle.message)
        }
        return Pair(true, "Notification send in success")
    }
}