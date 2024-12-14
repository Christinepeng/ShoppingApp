package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(
    onSearchBarFocused: () -> Unit,
    onNavigateToAuth: () -> Unit, // 新增導航參數
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // 搜尋欄
        SearchContent(
            query = "",
            onQueryChanged = {},
            onSearchClicked = {},
            onImageCaptured = {},
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 根據登入狀態顯示不同的區域
        when (val state = authState) {
            is AuthState.Success -> {
                // 已登入
                WelcomeSection(user = state.user)
            }

            else -> {
                // 未登入
                GuestSection(onNavigateToAuth = onNavigateToAuth)
            }
        }
    }
}

@Composable
fun WelcomeSection(user: FirebaseUser?) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Text(
            text = "Hi, ${user?.displayName ?: "User"}",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${user?.email ?: "0"} pts")
    }
}

@Composable
fun GuestSection(onNavigateToAuth: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Track your rewards, check order status...",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { onNavigateToAuth() }) {
                Text("Create Account")
            }
            OutlinedButton(onClick = { onNavigateToAuth() }) {
                Text("Sign In")
            }
        }
    }
}
