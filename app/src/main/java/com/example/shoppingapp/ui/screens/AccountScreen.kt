package com.example.shoppingapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.viewmodel.AccountViewModel

@Composable
fun AccountScreen(viewModel: AccountViewModel = hiltViewModel()) {
    val state = viewModel.state
    Text(text = "Account")
}
