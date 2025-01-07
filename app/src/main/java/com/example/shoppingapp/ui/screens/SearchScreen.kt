package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.FavoritesViewModel
import com.example.shoppingapp.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    query: String,
    favoritesViewModel: FavoritesViewModel, // 改成必傳
    onProductClicked: (String) -> Unit,
    onSearchBarFocused: () -> Unit,
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchResults by searchViewModel.searchResults.collectAsState()

    LaunchedEffect(query) {
        searchViewModel.onQueryChanged(query)
        searchViewModel.onSearchClicked()
    }

    Column {
        SearchContent(
            query = query,
            onQueryChanged = {},
            onSearchClicked = searchViewModel::onSearchClicked,
            onImageCaptured = searchViewModel::onImageCaptured,
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        if (searchResults.isEmpty()) {
            Text(
                text = "没有搜索结果",
                modifier = Modifier.padding(16.dp),
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(searchResults) { product: Product ->
                    val isFav = favoritesViewModel.isFavorite(product.id)
                    ProductItem(
                        product = product,
                        isFavorite = isFav,
                        onFavoriteClick = {
                            favoritesViewModel.toggleFavorite(product)
                        },
                        onClick = {
                            onProductClicked(product.id)
                        },
                    )
                }
            }
        }
    }
}
