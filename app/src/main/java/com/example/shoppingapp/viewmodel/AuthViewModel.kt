package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
        val authState: StateFlow<AuthState> = _authState.asStateFlow()

        init {
            // 初始化時檢查用戶是否已登入
            val user = authRepository.currentUser
            if (user != null) {
                _authState.value = AuthState.Success(user)
            }
        }

        fun signUp(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                val result = authRepository.signUp(email, password)
                if (result.isSuccess) {
                    _authState.value = AuthState.Success(result.getOrNull())
                } else {
                    _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "註冊失敗")
                }
            }
        }

        fun signIn(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                val result = authRepository.signIn(email, password)
                if (result.isSuccess) {
                    _authState.value = AuthState.Success(result.getOrNull())
                } else {
                    _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "登入失敗")
                }
            }
        }

        fun signOut() {
            authRepository.signOut()
            _authState.value = AuthState.Idle
        }
    }

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
