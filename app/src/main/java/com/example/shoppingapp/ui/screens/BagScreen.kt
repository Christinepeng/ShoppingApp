package com.example.shoppingapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.viewmodel.BagViewModel

@Composable
fun BagScreen(viewModel: BagViewModel = hiltViewModel()) {
    val state = viewModel.state
    Text(text = "Bag")
}
