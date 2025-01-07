package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val favoriteProducts = viewModel.favoriteProducts

    if (favoriteProducts.isEmpty()) {
        // 若沒有任何最愛商品
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(text = "No favorites yet!")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(favoriteProducts) { product ->
                // 傳入 isFavorite = true，並可點擊愛心切換
                ProductItem(
                    product = product,
                    isFavorite = true,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(product)
                    },
                    onClick = {
                        // 若要進入商品詳情，可自行導航
                        // e.g., navController.navigate(Screen.ProductDetailScreen.createRoute(product.id))
                    },
                )
            }
        }
    }
}
