package com.example.notifyall.screens.healper

object Validator {
    fun validateName(name: String): ValidateResult {
        return ValidateResult(
            name.isNotEmpty()
                    && name.length >= 6,
            "Name must be at least 6 characters"
        )
    }

    fun validateEmail(email: String): ValidateResult {
        return ValidateResult(
            email.isNotEmpty(),
            "Email must not be empty"
        )
    }

    fun validatePassword(password: String): ValidateResult {
        return ValidateResult(
            password.isNotEmpty()
                    && password.length >= 6,
            "Password must be at least 6 characters"
        )
    }

    fun validateRePassword(password: String, confirmPassword: String): ValidateResult {
        return ValidateResult(
            password == confirmPassword &&
                    password.isNotEmpty() && password.length >= 6,
            "Password must be at least 6 characters and match with confirm password"
        )
    }

    fun validateNotificationTitle(title: String): ValidateResult {
        return ValidateResult(
            title.isNotEmpty(),
            "Notification must not be empty"
        )
    }
}

data class ValidateResult(
    val status: Boolean = false,
    val message: String = ""
)