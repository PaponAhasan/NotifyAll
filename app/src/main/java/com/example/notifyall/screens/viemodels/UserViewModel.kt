package com.example.notifyall.screens.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,): ViewModel() {

    private val _navigateToLogin = MutableLiveData(false)
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    init {
        viewModelScope.launch {
            val isLoggedIn = firebaseAuth.currentUser != null
            _navigateToLogin.value = isLoggedIn
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _navigateToLogin.value = false
    }

}