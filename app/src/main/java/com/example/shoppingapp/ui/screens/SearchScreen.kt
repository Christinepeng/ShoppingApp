package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.ui.components.SearchContent

@Composable
fun SearchScreen(
    query: String,
    searchSuggestions: List<String>,
    searchResults: List<Product>,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onSuggestionClicked: (String) -> Unit,
    onImageCaptured: (android.graphics.Bitmap) -> Unit,
    onProductClicked: (String) -> Unit,
    onShowSuggestions: (String) -> Unit, // 新增参数
    originalContent: @Composable () -> Unit,
) {
    Column {
        SearchContent(
            query = query,
            onQueryChanged = onQueryChanged,
            onSearchClicked = onSearchClicked,
            onImageCaptured = onImageCaptured,
            showBarcodeScanner = true,
            onShowSuggestions = { currentQuery ->
                onShowSuggestions(currentQuery)
            },
        )

        // 当 query 为空且没有建议或结果时显示原始内容
        if (query.isEmpty() && searchSuggestions.isEmpty() && searchResults.isEmpty()) {
            originalContent()
        }
    }
}
