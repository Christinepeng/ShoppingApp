package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()

    object Loading : AuthState()

    data class Success(
        val user: FirebaseUser?,
    ) : AuthState()

    data class Error(
        val message: String,
    ) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // 使用 FirebaseAuth 創建用戶
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                _authState.value = AuthState.Success(user)
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
                // 使用 FirebaseAuth 登錄用戶
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                _authState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
