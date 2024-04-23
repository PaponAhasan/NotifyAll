package com.example.notifyall.di

import com.example.notifyall.repository.UserRepository
import com.example.notifyall.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()
    @Provides
    @Singleton
    fun providesFireStore() = FirebaseFirestore.getInstance()
    @Provides
    @Singleton
    fun providesUserRepository(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore) :
            UserRepository = UserRepositoryImpl(firebaseAuth, firebaseFirestore)
}