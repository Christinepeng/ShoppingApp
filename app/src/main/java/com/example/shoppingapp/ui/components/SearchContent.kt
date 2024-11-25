package com.example.shoppingapp.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.model.Product

@Composable
fun SearchContent(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onImageCaptured: (Bitmap) -> Unit,
    searchSuggestions: List<String>,
    onSuggestionClicked: (String) -> Unit,
    searchResults: List<Product>,
    onProductClicked: (Product) -> Unit,
    showBarcodeScanner: Boolean = false
) {
    CameraHandler(onImageCaptured = onImageCaptured) { launchCamera ->
        Column {
            SearchBar(
                query = query,
                onQueryChanged = onQueryChanged,
                onSearchClicked = onSearchClicked,
                onBarcodeScanClicked = if (showBarcodeScanner) launchCamera else null
            )

            // 显示搜索建议
            if (searchSuggestions.isNotEmpty()) {
                LazyColumn {
                    items(searchSuggestions) { suggestion ->
                        Text(
                            text = suggestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSuggestionClicked(suggestion) }
                                .padding(8.dp)
                        )
                    }
                }
            }

            // 显示搜索结果
            if (searchResults.isNotEmpty()) {
                LazyColumn {
                    items(searchResults) { product ->
                        ProductItem(product = product, onClick = { onProductClicked(product) })
                    }
                }
            }
        }
    }
}
