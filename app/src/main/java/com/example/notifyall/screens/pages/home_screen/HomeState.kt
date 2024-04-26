package com.example.notifyall.screens.pages.home_screen

data class HomeState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)