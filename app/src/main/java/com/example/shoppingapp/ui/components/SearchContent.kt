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
    onSearchBarFocused: () -> Unit,
) {
    CameraHandler(onImageCaptured = onImageCaptured) { launchCamera ->
        Column {
            SearchBar(
                query = query,
                onQueryChanged = onQueryChanged,
                onSearchClicked = onSearchClicked,
                onBarcodeScanClicked = if (showBarcodeScanner) launchCamera else null,
                onSearchBarFocused = onSearchBarFocused,
            )

            // 原始内容在 SearchScreen 中处理
        }
    }
}
