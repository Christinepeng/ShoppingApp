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

    // 建立 SnackbarHostState 用於顯示訊息
    val snackbarHostState = remember { SnackbarHostState() }

    // 當 authState 改變時，若成功則顯示 Snackbar 並呼叫 onAuthSuccess()
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                snackbarHostState.showSnackbar("Authentication successful!")
                onAuthSuccess()
            }
            is AuthState.Error -> {
                snackbarHostState.showSnackbar("Error: ${(authState as AuthState.Error).message}")
            }
            else -> { /* Idle或Loading狀態不顯示Snackbar */ }
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
            Text(
                text = if (isSignUp) "註冊" else "登入",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密碼") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
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
            TextButton(onClick = { isSignUp = !isSignUp }) {
                Text(text = if (isSignUp) "已有帳號？登入" else "沒有帳號？註冊")
            }
        }
    }
}
