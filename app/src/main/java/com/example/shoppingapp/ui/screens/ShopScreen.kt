package com.example.shoppingapp.ui.screens

import com.example.shoppingapp.viewmodel.ShopViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShopScreen(viewModel: ShopViewModel = hiltViewModel()) {
    val state = viewModel.state
    Text(text = "Shop")
}