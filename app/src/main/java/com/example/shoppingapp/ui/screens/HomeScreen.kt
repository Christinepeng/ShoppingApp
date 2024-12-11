package com.example.shoppingapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.viewmodel.SearchViewModel

@Composable
fun HomeScreen(
    onShowResults: (String) -> Unit,
    onProductClicked: (String) -> Unit,
    onShowSuggestions: (String) -> Unit, // 新增参数
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val query by viewModel.query.collectAsState()
    val searchSuggestions by viewModel.searchSuggestions.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    SearchScreen(
        query = query,
        searchSuggestions = searchSuggestions,
        searchResults = searchResults,
        onQueryChanged = { viewModel.onQueryChanged(it) },
        onSearchClicked = {
            viewModel.onSearchClicked()
            onShowResults(query) // 导航到 SearchResults 页面
        },
        onSuggestionClicked = { suggestion ->
            viewModel.onSuggestionClicked(suggestion)
            onShowResults(query)
        },
        onImageCaptured = { bitmap ->
            viewModel.onImageCaptured(bitmap)
            onShowResults(query)
        },
        onProductClicked = onProductClicked,
        onShowSuggestions = onShowSuggestions, // 传递回调
        originalContent = { Text("Home Original Content") },
    )
}
