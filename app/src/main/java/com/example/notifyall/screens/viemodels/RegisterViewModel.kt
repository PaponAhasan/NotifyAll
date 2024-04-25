package com.example.notifyall.screens.viemodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyall.models.User
import com.example.notifyall.repository.UserRepository
import com.example.notifyall.screens.healper.FieldsState
import com.example.notifyall.screens.healper.Validator
import com.example.notifyall.screens.pages.register_screen.RegisterState
import com.example.notifyall.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _registerState = Channel<RegisterState>()
    val registerState = _registerState.receiveAsFlow()

    private val _userSaveState = Channel<RegisterState>()
    val userSaveState = _userSaveState.receiveAsFlow()

    var registerFieldState = mutableStateOf(FieldsState())

    fun register(email: String, password: String) = viewModelScope.launch {
        repository.register(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _registerState.send(RegisterState(isSuccess = "Register in success"))
                }

                is Resource.Loading -> {
                    _registerState.send(RegisterState(isLoading = true))
                }

                is Resource.Error -> {
                    _registerState.send(RegisterState(isError = result.message))
                }
            }
        }
    }

    fun setUserData(user: User) = viewModelScope.launch {
        repository.setUserData(user).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _userSaveState.send(RegisterState(isSuccess = "Save user in success"))
                }

                is Resource.Loading -> {
                    _userSaveState.send(RegisterState(isLoading = true))
                }

                is Resource.Error -> {
                    _userSaveState.send(RegisterState(isError = result.message))
                }
            }
        }
    }

    fun validateInputField(): Pair<Boolean, String> {
        val userName = Validator.validateName(
            registerFieldState.value.name
        )
        val userEmail = Validator.validateEmail(
            registerFieldState.value.email
        )
        val userPassword = Validator.validatePassword(
            registerFieldState.value.password
        )
        val userConfirmPassword = Validator.validateRePassword(
            registerFieldState.value.password, registerFieldState.value.confirmPassword
        )
        Log.d("TAG", "validateInputField: $userName $userEmail $userPassword $userConfirmPassword")
        // Set error state
        registerFieldState.value = registerFieldState.value.copy(
            nameError = userName.status,
            emailError = userEmail.status,
            passwordError = userPassword.status,
            confirmPasswordError = userConfirmPassword.status
        )
        // get error message
        if (!userEmail.status) {
            return Pair(false, userEmail.message)
        } else if (!userName.status) {
            return Pair(false, userName.message)
        } else if (!userPassword.status) {
            return Pair(false, userPassword.message)
        } else if (!userConfirmPassword.status) {
            return Pair(false, userConfirmPassword.message)
        }
        return Pair(true, "Register in success")
    }
}