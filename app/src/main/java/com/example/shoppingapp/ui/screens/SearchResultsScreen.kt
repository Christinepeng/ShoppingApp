package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.SearchViewModel

@Composable
fun SearchResultsScreen(
    query: String,
    onProductClicked: (String) -> Unit,
    onBack: () -> Unit,
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val results by viewModel.searchResults.collectAsState()

    // 记录查询参数变化，避免重复搜索
    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            viewModel.onQueryChanged(query)
            viewModel.onSearchClicked()
        }
    }

    // 显示结果列表
    if (results.isEmpty()) {
        Text(
            text = "没有搜索结果",
            modifier = Modifier.padding(16.dp),
        )
    } else {
        LazyColumn {
            items(results) { product ->
                ProductItem(
                    product = product,
                    onClick = { onProductClicked(product.id) },
                )
            }
        }
    }
}
