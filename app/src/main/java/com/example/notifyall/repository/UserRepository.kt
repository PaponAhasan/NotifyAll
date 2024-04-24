package com.example.notifyall.repository

import com.example.notifyall.models.User
import com.example.notifyall.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(email: String, password: String): Flow<Resource<AuthResult>>
    fun register(email: String, password: String): Flow<Resource<AuthResult>>
    fun storeUserData(user: User): Flow<Resource<Void>>

    fun getCurrentUser(): Flow<Boolean>
    fun logout()
}