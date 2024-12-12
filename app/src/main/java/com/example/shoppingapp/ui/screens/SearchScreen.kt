package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    query: String,
    onProductClicked: (String) -> Unit,
    onSearchBarFocused: () -> Unit,
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(query) {
        viewModel.onQueryChanged(query)
        viewModel.onSearchClicked()
    }

    Column {
        SearchContent(
            query = query,
            onQueryChanged = {},
            onSearchClicked = viewModel::onSearchClicked,
            onImageCaptured = viewModel::onImageCaptured,
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        // 显示结果列表
        if (searchResults.isEmpty()) {
            Text(
                text = "没有搜索结果",
                modifier = Modifier.padding(16.dp),
            )
        } else {
            LazyColumn {
                items(searchResults) { product ->
                    ProductItem(
                        product = product,
                        onClick = { onProductClicked(product.id) },
                    )
                }
            }
        }
    }
}
