package com.example.notifyall.repository

import com.example.notifyall.models.User
import com.example.notifyall.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(email: String, password: String): Flow<Resource<AuthResult>>
    fun register(email: String, password: String): Flow<Resource<AuthResult>>
    fun setUserData(user: User): Flow<Resource<Void>>
    fun getUserData(): Flow<Resource<User>>
    fun getCurrentUserType(): Flow<Resource<Boolean>>
    fun getCurrentUser(): Flow<Boolean>
    fun logout():  Flow<Resource<Unit>>
}