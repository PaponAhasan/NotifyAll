package com.example.notifyall.repository

import com.example.notifyall.models.User
import com.example.notifyall.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRepository {
    override fun login(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow<Resource<AuthResult>> {
            emit(Resource.Loading())
            val result: AuthResult =
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun register(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow<Resource<AuthResult>> {
            emit(Resource.Loading())
            val result: AuthResult =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun storeUserData(user: User): Flow<Resource<Void>> {
        return flow<Resource<Void>> {
            emit(Resource.Loading())
            val result = firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid ?: "")
                .set(user).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getCurrentUser(): Flow<Boolean> {
        return flow {
            firebaseAuth.currentUser != null
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}