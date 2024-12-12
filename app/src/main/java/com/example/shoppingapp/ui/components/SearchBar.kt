package com.example.shoppingapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onBarcodeScanClicked: (() -> Unit)? = null,
    onSearchBarFocused: (() -> Unit)? = null,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged(it) },
            modifier =
                Modifier
                    .weight(1f)
                    .height(56.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && onSearchBarFocused != null) {
                            onSearchBarFocused()
                        }
                    },
            placeholder = { Text("Search products") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onSearchClicked) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
        )
        if (onBarcodeScanClicked != null) {
            IconButton(onClick = onBarcodeScanClicked) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Scan Barcode")
            }
        }
    }
}
