package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.navigation.Screen
import com.example.shoppingapp.viewmodel.AccountViewModel
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun AccountScreen(
    authViewModel: AuthViewModel,
    viewModel: AccountViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit, // 用來導航至子頁面
    onNavigateToAuth: () -> Unit, // 新增: 未登入時點擊 -> 跳轉 Auth
) {
    // 觀察當前登入狀態
    val authState by authViewModel.authState.collectAsState()
    val isLoggedIn = authState is AuthState.Success

    // 六個可點擊項目的資料 (照需求顯示)
    val menuItems =
        listOf(
            "Purchases & Returns" to Screen.PurchasesReturnsScreen.route,
            "Star Rewards" to Screen.StarRewardsScreen.route,
            "Get Help & Share Feedback" to Screen.GetHelpFeedbackScreen.route,
            "Wallet" to Screen.WalletScreen.route,
            "Profile" to Screen.ProfileScreen.route,
            "Privacy Setting" to Screen.PrivacySettingScreen.route,
        )

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        // 依需求顯示「Welcome ...」等文字
        Text(
            text = if (isLoggedIn) "Welcome back!" else "Guest Account",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 顯示六個點擊項目 (是否要在未登入時隱藏，視設計需求決定；此處範例直接都顯示)
        LazyColumn {
            items(menuItems) { (title, route) ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onItemClicked(route) }
                            .padding(vertical = 12.dp),
                )
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 根據是否登入切換按鈕文字及行為
        if (isLoggedIn) {
            // 已登入 -> Sign Out 按鈕
            Button(
                onClick = { authViewModel.signOut() },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Sign Out")
            }
        } else {
            // 未登入 -> Create Account / Sign In
            Button(
                onClick = { onNavigateToAuth() },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Create Account / Sign In")
            }
        }
    }
}
