package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.BagItem
import com.example.shoppingapp.viewmodel.BagViewModel
import com.example.shoppingapp.viewmodel.FavoritesViewModel

@Composable
fun BagScreen(
    favoritesViewModel: FavoritesViewModel,
    bagViewModel: BagViewModel,
) {
    val bagItems = bagViewModel.bagItems

    if (bagItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Your bag is empty")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(bagItems) { bagItem: BagItem ->
                val product = bagItem.product

                // 1) Bag 狀態
                val quantity = bagItem.quantity // 或 bagViewModel.getQuantity(product.id)

                // 2) Favorites 狀態
                val isFav = favoritesViewModel.isFavorite(product.id)

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
                        // 雖然已在購物車，不常見，但你可讓它 quantity++ if needed
                        bagViewModel.addToBag(product)
                    },
                    onIncrementClick = {
                        bagViewModel.incrementQuantity(product)
                    },
                    onDecrementClick = {
                        bagViewModel.decrementQuantity(product)
                    },
                    onClick = {
                        // e.g. navController.navigate(...)
                    },
                )
            }
        }
    }
}
