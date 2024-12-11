package com.example.shoppingapp.ui.screens

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.ShopViewModel

@Composable
fun ShopScreen(onSearchBarFocused: () -> Unit) {
    val viewModel: ShopViewModel = hiltViewModel()

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
