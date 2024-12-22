package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(
    onSearchBarFocused: () -> Unit,
    onNavigateToAuth: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.authState.collectAsState()

    // 整個螢幕是一個 Column（固定高度）
    Column(modifier = Modifier.fillMaxSize()) {
        // 1) 固定在頂部的搜尋欄
        SearchContent(
            query = "",
            onQueryChanged = {},
            onSearchClicked = {},
            onImageCaptured = {},
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        // 2) 其餘內容可垂直捲動：登入功能 + 促銷圖片
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 根據登入狀態顯示不同區塊
            when (val state = authState) {
                is AuthState.Success -> {
                    WelcomeSection(user = state.user)
                }
                else -> {
                    GuestSection(onNavigateToAuth = onNavigateToAuth)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 促銷圖片區域
            PromotionsBannerScreen()

            // 若有更多內容，可繼續往下添加
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
            text = "Hi, ${user?.email ?: "User"}",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${user?.email ?: ""} pts")
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
