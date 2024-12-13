// package: com.example.shoppingapp.ui.screens

package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    onSearchBarFocused: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // 您現有的 SearchContent
        SearchContent(
            query = "",
            onQueryChanged = {},
            onSearchClicked = {},
            onImageCaptured = {},
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 使用局部變數來進行智能轉換
        when (val state = authState) {
            is AuthState.Idle, is AuthState.Error -> AuthSection(state, authViewModel)
            is AuthState.Loading -> LoadingIndicator()
            is AuthState.Success -> WelcomeSection(state.user, authViewModel)
        }
    }
}

@Composable
fun AuthSection(
    state: AuthState,
    authViewModel: AuthViewModel,
) {
    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Text(text = if (isSignUp) "註冊" else "登入", style = MaterialTheme.typography.titleMedium)
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
        TextButton(
            onClick = { isSignUp = !isSignUp },
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(text = if (isSignUp) "已有帳號？登入" else "沒有帳號？註冊")
        }
    }

    // 顯示錯誤訊息
    if (state is AuthState.Error) {
        val errorMessage = state.message
        Snackbar(
            action = { /* 可選的重試按鈕 */ },
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun WelcomeSection(
    user: com.google.firebase.auth.FirebaseUser?,
    authViewModel: AuthViewModel,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = "歡迎，${user?.email}")
        Button(onClick = { authViewModel.signOut() }) {
            Text(text = "登出")
        }
    }
}
