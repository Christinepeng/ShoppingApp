package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.navigation.Screen
import com.example.shoppingapp.viewmodel.AccountViewModel
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun AccountScreen(
    authViewModel: AuthViewModel,
    viewModel: AccountViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit, // 用來導航至子頁面
) {
//    val state = viewModel.state

    // 6 個可點擊項目的資料，Item = (標題, route)
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
        Text(
            text = "Welcome back,...",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 顯示 6 個可點擊項目
        LazyColumn {
            items(menuItems) { (title, route) ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClicked(route)
                            }.padding(vertical = 12.dp),
                )
                Divider()
            }
        }

        // 最後放一個 Spacer，再放 Logout 按鈕
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { authViewModel.signOut() },
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Logout")
        }
    }
}
