package com.example.shoppingapp.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable

@Composable
fun SearchContent(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onImageCaptured: (Bitmap) -> Unit,
    showBarcodeScanner: Boolean = false,
    onShowSuggestions: (String) -> Unit, // 新增参数
) {
    CameraHandler(onImageCaptured = onImageCaptured) { launchCamera ->
        Column {
            SearchBar(
                query = query,
                onQueryChanged = onQueryChanged,
                onSearchClicked = onSearchClicked,
                onBarcodeScanClicked = if (showBarcodeScanner) launchCamera else null,
            )

            // 当 query 不为空时，触发导航到 SuggestionListScreen
            if (query.isNotEmpty()) {
                onShowSuggestions(query)
            }

            // 原始内容在 SearchScreen 中处理
        }
    }
}
