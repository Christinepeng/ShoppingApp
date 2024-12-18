package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    authViewModel: AuthViewModel,
) {
    // 跟之前一樣的邏輯，唯一差別是不要在這裡 hiltViewModel()，改用傳入的 authViewModel
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
                        authViewModel.checkEmailVerification()
                    },
                ) {
                    Text("I have verified my email")
                }
            }
        }
    }
}
