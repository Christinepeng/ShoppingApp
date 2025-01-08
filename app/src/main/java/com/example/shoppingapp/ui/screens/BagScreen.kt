package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.BagItem
import com.example.shoppingapp.viewmodel.BagViewModel

@Composable
fun BagScreen(bagViewModel: BagViewModel) {
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
                // 從 BagViewModel 取得當前數量
                val quantity = bagItem.quantity // 或 bagViewModel.getQuantity(bagItem.product.id)

                ProductItem(
                    product = bagItem.product,
                    // 如果有需要也可顯示 Favorites 狀態，
                    // 這裡示範只顯示購物車邏輯:
                    isFavorite = false,
                    onFavoriteClick = { /* do nothing or remove if not needed */ },
                    // Bag logic
                    bagQuantity = quantity,
                    onAddToBagClick = {
                        bagViewModel.addToBag(bagItem.product)
                    },
                    onIncrementClick = {
                        bagViewModel.incrementQuantity(bagItem.product)
                    },
                    onDecrementClick = {
                        bagViewModel.decrementQuantity(bagItem.product)
                    },
                    onClick = {
                        // 若要點擊商品 -> 詳情，可視需要實作
                    },
                )
            }
        }
    }
}
