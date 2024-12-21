package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()

    object Loading : AuthState()

    data class Success(
        val user: FirebaseUser?,
    ) : AuthState()

    data class Error(
        val message: String,
    ) : AuthState()

    data class EmailVerificationSent(
        val user: FirebaseUser?,
    ) : AuthState()
}

@HiltViewModel
class AuthViewModel
    @Inject
    constructor() : ViewModel() {
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()

        private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
        val authState: StateFlow<AuthState> = _authState

        fun signUp(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val result = auth.createUserWithEmailAndPassword(email, password).await()
                    val user = result.user
                    if (user != null) {
                        user.sendEmailVerification().await()
                        _authState.value = AuthState.EmailVerificationSent(user)
                    } else {
                        _authState.value = AuthState.Error("Failed to create user.")
                    }
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Unknown Error")
                }
            }
        }

        fun signIn(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val result = auth.signInWithEmailAndPassword(email, password).await()
                    val user = result.user
                    if (user != null && user.isEmailVerified) {
                        _authState.value = AuthState.Success(user)
                    } else if (user != null && !user.isEmailVerified) {
                        _authState.value = AuthState.EmailVerificationSent(user)
                    } else {
                        _authState.value = AuthState.Error("Sign in failed.")
                    }
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Unknown Error")
                }
            }
        }

        fun checkEmailVerification() {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                val currentUser = auth.currentUser
                currentUser?.reload()?.await()
                if (currentUser != null && currentUser.isEmailVerified) {
                    _authState.value = AuthState.Success(currentUser)
                } else if (currentUser != null && !currentUser.isEmailVerified) {
                    _authState.value = AuthState.EmailVerificationSent(currentUser)
                } else {
                    _authState.value = AuthState.Error("No user to verify.")
                }
            }
        }

        fun signOut() {
            auth.signOut()
            _authState.value = AuthState.Idle
        }
    }
