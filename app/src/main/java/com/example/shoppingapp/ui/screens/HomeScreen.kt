package com.example.shoppingapp.ui.screens

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(onSearchBarFocused: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    // When SearchBar in SearchContent is selected, call `onSearchBarFocused`
    SearchContent(
        query = "",
        onQueryChanged = {},
        onSearchClicked = {},
        onImageCaptured = {},
        showBarcodeScanner = true,
        onSearchBarFocused = onSearchBarFocused,
    )
}
