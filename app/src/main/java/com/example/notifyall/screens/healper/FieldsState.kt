package com.example.notifyall.screens.healper

data class FieldsState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var name: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var confirmPasswordError: Boolean = false,
    var nameError: Boolean = false,
)
