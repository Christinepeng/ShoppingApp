package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.viewmodel.AccountViewModel
import com.example.shoppingapp.viewmodel.AuthViewModel

@Composable
fun AccountScreen(
    authViewModel: AuthViewModel,
    viewModel: AccountViewModel =
        androidx.hilt.navigation.compose
            .hiltViewModel(),
) {
    val state = viewModel.state
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Text(text = "Account Screen: $state")
        Button(
            onClick = { authViewModel.signOut() },
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Logout")
        }
    }
}
