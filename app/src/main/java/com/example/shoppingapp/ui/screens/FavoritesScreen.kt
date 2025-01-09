package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.BagViewModel
import com.example.shoppingapp.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel,
    bagViewModel: BagViewModel,
) {
    // 取出「我的最愛」列表
    val favoriteProducts = favoritesViewModel.favoriteProducts

    if (favoriteProducts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "No favorites yet!")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(favoriteProducts) { product ->
                // 1) Favorites 狀態：此商品一定是favorite，但還是要讓使用者能取消最愛
                val isFav = true // 或 favoritesViewModel.isFavorite(product.id)

                // 2) Bag 狀態：取得目前購物車數量
                val quantity = bagViewModel.getQuantity(product.id)

                ProductItem(
                    product = product,
                    // Favorites
                    isFavorite = isFav,
                    onFavoriteClick = {
                        favoritesViewModel.toggleFavorite(product)
                    },
                    // Bag
                    bagQuantity = quantity,
                    onAddToBagClick = {
                        bagViewModel.addToBag(product)
                    },
                    onIncrementClick = {
                        bagViewModel.incrementQuantity(product)
                    },
                    onDecrementClick = {
                        bagViewModel.decrementQuantity(product)
                    },
                    // 點擊整個商品 -> 可自行導航
                    onClick = {
                        // e.g. navController.navigate(...)
                    },
                )
            }
        }
    }
}
