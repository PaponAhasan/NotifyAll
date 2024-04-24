package com.example.notifyall.screens.pages.login_screen

data class LogInState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)