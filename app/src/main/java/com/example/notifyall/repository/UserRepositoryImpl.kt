package com.example.notifyall.repository

import com.example.notifyall.models.User
import com.example.notifyall.util.Constants.USER_COLLECTION
import com.example.notifyall.util.Constants.USER_FIELD_IS_ADMIN
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

    private val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid

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

    override fun setUserData(user: User): Flow<Resource<Void>> {
        return flow<Resource<Void>> {
            // Check if user is logged in
            if (currentUserId == null) {
                emit(Resource.Error("Not logged in"))
                return@flow // Exit the flow if not logged in
            }

            emit(Resource.Loading())
            val result =
                firebaseFirestore.collection(USER_COLLECTION).document(currentUserId ?: "")
                    .set(user).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getUserData(): Flow<Resource<User>> {
        return flow<Resource<User>> {
            // Check if user is logged in
            if (currentUserId == null) {
                emit(Resource.Error("Not logged in"))
                return@flow // Exit the flow if not logged in
            }

            emit(Resource.Loading())
            val userSnapshot = firebaseFirestore.collection(USER_COLLECTION)
                .document(currentUserId ?: "")
                .get()
                .await()

            if (!userSnapshot.exists()) {
                emit(Resource.Error("User data not found"))
                return@flow
            }

            val user = userSnapshot.toObject(User::class.java)
            if (user == null) {
                emit(Resource.Error("User data not found"))
                return@flow
            }
            emit(Resource.Success(user))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getCurrentUserType(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            // Check if user is logged in
            if (currentUserId == null) {
                emit(Resource.Error("Not logged in"))
                return@flow // Exit the flow if not logged in
            }

            emit(Resource.Loading())
            // Filter based on isAdmin field
            val isAdmin = firebaseFirestore.collection(USER_COLLECTION)
                .document(currentUserId ?: "")
                .get()
                .await()
                .getBoolean(USER_FIELD_IS_ADMIN) ?: false // Handle potential null value
            emit(Resource.Success(isAdmin))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getCurrentUser(): Flow<Boolean> {
        return flow {
            firebaseAuth.currentUser != null
        }
    }

    override fun logout(): Flow<Resource<Unit>> {
        return flow<Resource<Unit>> {
            emit(Resource.Loading())
            val result = firebaseAuth.signOut()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}