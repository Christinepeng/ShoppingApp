package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    // 若購物車為空
    if (bagItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Your bag is empty")
        }
    } else {
        // 計算 Subtotal, Shipping, Pre-tax
        val subtotal = bagItems.sumOf { it.product.price * it.quantity }
        val shipping = if (subtotal > 50000) 0.0 else 10000.95
        val preTaxTotal = subtotal + shipping

        // 外層用 Column：上方放 LazyColumn (可捲動的商品 + Order Summary)，
        // 下方放結帳按鈕 (固定)
        Column(modifier = Modifier.fillMaxSize()) {
            // 1) LazyColumn：商品清單 + 加粗分隔線 + Order Summary
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f) // 占滿剩餘空間
                        .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 商品清單
                items(bagItems) { bagItem: BagItem ->
                    val product = bagItem.product
                    val quantity = bagItem.quantity
                    val isFav = favoritesViewModel.isFavorite(product.id)

                    ProductItem(
                        product = product,
                        isFavorite = isFav,
                        onFavoriteClick = {
                            favoritesViewModel.toggleFavorite(product)
                        },
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
                        onClick = {
                            // e.g. navController.navigate(...)
                        },
                    )
                }

                // ** 在商品清單後面加一條“粗厚的淺灰色分隔線” (10倍厚度) **
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        thickness = 10.dp, // 加粗 10倍
                        color = Color(0xFFEEEEEE), // 很淺的淺灰色
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ** Order Summary 區塊 **
                item {
                    Text(
                        text = "Order Summary",
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtotal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Subtotal",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "$${"%.2f".format(subtotal)}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    // Shipping
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Shipping",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        val shippingText = if (shipping == 0.0) "$0.00" else "$${"%.2f".format(shipping)}"
                        Text(
                            text = shippingText,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    // Pre-tax order total
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Pre-tax order total",
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                        Text(
                            text = "$${"%.2f".format(preTaxTotal)}",
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // 2) 最底下區域：一條細線 + Checkout 按鈕 (固定不捲動)
            Divider(
                thickness = 1.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // TODO: checkout 邏輯
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text("Checkout")
            }
        }
    }
}
