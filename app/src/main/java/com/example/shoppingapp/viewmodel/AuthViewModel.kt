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

    // 新增 EmailVerificationSent 狀態
    data class EmailVerificationSent(
        val user: FirebaseUser?,
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
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    // 寄出驗證信
                    user.sendEmailVerification().await()
                    // 更新狀態為 EmailVerificationSent，等待使用者驗證
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
                // 登入時也可檢查是否已驗證
                if (user != null && user.isEmailVerified) {
                    _authState.value = AuthState.Success(user)
                } else if (user != null && !user.isEmailVerified) {
                    // 若使用者未驗證Email，要求使用者先驗證
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
            if (currentUser != null) {
                // 重新載入使用者資訊
                currentUser.reload().await()
                if (currentUser.isEmailVerified) {
                    // 驗證成功
                    _authState.value = AuthState.Success(currentUser)
                } else {
                    // 尚未驗證，回到 EmailVerificationSent 狀態
                    _authState.value = AuthState.EmailVerificationSent(currentUser)
                }
            } else {
                _authState.value = AuthState.Error("No user to verify.")
            }
        }
    }
}
