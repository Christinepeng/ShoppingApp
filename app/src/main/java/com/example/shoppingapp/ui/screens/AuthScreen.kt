package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                snackbarHostState.showSnackbar("Authentication successful!")
                onAuthSuccess()
            }
            is AuthState.Error -> {
                val msg = (authState as AuthState.Error).message
                snackbarHostState.showSnackbar("Error: $msg")
            }
            // EmailVerificationSent 狀態在UI中顯示相關提示，不立即導航
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(paddingValues),
        ) {
            // 標題顯示
            Text(
                text = if (isSignUp) "註冊" else "登入",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 輸入 Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 輸入密碼
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密碼") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 按鈕：註冊或登入
            Button(
                onClick = {
                    if (isSignUp) {
                        authViewModel.signUp(email, password)
                    } else {
                        authViewModel.signIn(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = if (isSignUp) "註冊" else "登入")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 切換註冊/登入模式
            TextButton(onClick = { isSignUp = !isSignUp }) {
                Text(text = if (isSignUp) "已有帳號？登入" else "沒有帳號？註冊")
            }

            // 若目前狀態為 EmailVerificationSent，顯示提示訊息與「我已驗證」按鈕
            if (authState is AuthState.EmailVerificationSent) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text =
                        "A verification email has been sent to $email. " +
                            "Please verify your email before proceeding.",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // 使用者點選「我已驗證」後，檢查是否完成驗證
                        authViewModel.checkEmailVerification()
                    },
                ) {
                    Text("I have verified my email")
                }
            }
        }
    }
}
