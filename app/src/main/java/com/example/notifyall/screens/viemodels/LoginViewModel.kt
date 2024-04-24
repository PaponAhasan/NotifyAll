package com.example.notifyall.screens.viemodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyall.repository.UserRepository
import com.example.notifyall.screens.healper.FieldsState
import com.example.notifyall.screens.healper.Validator
import com.example.notifyall.screens.pages.login_screen.LogInState
import com.example.notifyall.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _logInState = Channel<LogInState>()
    val logInState = _logInState.receiveAsFlow()

    var logInFieldState = mutableStateOf(FieldsState())

    fun login(email: String, password: String) = viewModelScope.launch {
        repository.login(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _logInState.send(LogInState(isSuccess = "Login in success"))
                }

                is Resource.Loading -> {
                    _logInState.send(LogInState(isLoading = true))
                }

                is Resource.Error -> {
                    _logInState.send(LogInState(isError = result.message))
                }
            }
        }
    }

    fun validateInputField(): Pair<Boolean, String>{
        val userEmail = Validator.validateEmail(
            logInFieldState.value.email
        )
        val userPassword = Validator.validatePassword(
            logInFieldState.value.password
        )
        Log.d("TAG", "validateInputField:$userEmail $userPassword")
        // Set error state
        logInFieldState.value = logInFieldState.value.copy(
            emailError = userEmail.status,
            passwordError = userPassword.status,
        )
        // set error message
        if (!userEmail.status) {
            return Pair(false, userEmail.message)
        } else if (!userPassword.status) {
            return Pair(false, userPassword.message)
        }
        return Pair(true, "Login in success")
    }
}