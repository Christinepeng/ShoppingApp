package com.example.shoppingapp.ui.screens

import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.ui.navigation.Screen
import com.example.shoppingapp.viewmodel.ShopViewModel

@Composable
fun ShopScreen(navController: NavController, viewModel: ShopViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val searchSuggestions by viewModel.searchSuggestions.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        if (currentBackStackEntry?.destination?.route == Screen.Shop.route) {
            viewModel.resetState()
        }
    }

    SearchContent(
        query = query,
        onQueryChanged = { viewModel.onQueryChanged(it) },
        onSearchClicked = { viewModel.onSearchClicked() },
        onImageCaptured = { bitmap -> viewModel.onImageCaptured(bitmap) },
        searchSuggestions = searchSuggestions,
        onSuggestionClicked = { suggestion -> viewModel.onSuggestionClicked(suggestion) },
        searchResults = searchResults,
        onProductClicked = { product ->
            navController.navigate(Screen.ProductDetail.route + "/${product.id}")
        },
        showBarcodeScanner = true // 如果需要条码扫描功能，设为 true
    )
}
