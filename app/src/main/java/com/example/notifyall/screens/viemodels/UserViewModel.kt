package com.example.notifyall.screens.viemodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyall.repository.UserRepository
import com.example.notifyall.screens.healper.FieldsState
import com.example.notifyall.screens.pages.home_screen.HomeState
import com.example.notifyall.screens.pages.register_screen.RegisterState
import com.example.notifyall.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repository: UserRepository
    ): ViewModel() {

    private val _navigateToLogin = MutableLiveData(false)
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    private val _userTypeState = Channel<HomeState>()
    val userTypeState = _userTypeState.receiveAsFlow()

    var userTypeValue = MutableLiveData(false)

    init {
        viewModelScope.launch {
            val isLoggedIn = firebaseAuth.currentUser != null
            _navigateToLogin.value = isLoggedIn
        }
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            _navigateToLogin.value = false
        }
    }

    fun getCurrentUserType() = viewModelScope.launch {
        repository.getCurrentUserType().collect {result ->
            when (result) {
                is Resource.Success -> {
                    val userType = result.data // Assuming result.data contains the user type
                    userTypeValue.value = userType
                    _userTypeState.send(HomeState(isSuccess = ""))
                }

                is Resource.Loading -> {
                    _userTypeState.send(HomeState(isLoading = true))
                }

                is Resource.Error -> {
                    _userTypeState.send(HomeState(isError = result.message))
                }
            }
        }
    }
}