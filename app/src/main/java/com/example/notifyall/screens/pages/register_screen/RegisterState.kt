package com.example.notifyall.screens.pages.register_screen

data class RegisterState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)