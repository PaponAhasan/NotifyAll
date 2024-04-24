package com.example.notifyall.screens.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyall.repository.UserRepository
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
}