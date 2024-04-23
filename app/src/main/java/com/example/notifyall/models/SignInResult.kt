package com.example.notifyall.models

data class SignInResult(
    val data: UserData,
    val errorMessage: String?
)

data class UserData(
    val token: String,
    val userName: String,
    val email: String,
    val phoneNumber: String,
    val userId: String,
    val role: String
)