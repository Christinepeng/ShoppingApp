package com.example.shoppingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.model.Product

@Composable
fun ProductItem(
    product: Product,
    // Favorites
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    // Bag
    bagQuantity: Int = 0,
    onAddToBagClick: (() -> Unit)? = null,
    onIncrementClick: (() -> Unit)? = null,
    onDecrementClick: (() -> Unit)? = null,
    // 點擊整個商品區塊 -> 進商品詳情
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // 商品圖片
            val imageUrl =
                product.thumbnail?.takeIf { it.isNotEmpty() }
                    ?: "https://default-thumbnail-url.com/image.png"

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = product.title ?: "Product Image",
                modifier =
                    Modifier
                        .size(64.dp)
                        .padding(end = 8.dp),
                contentScale = ContentScale.Crop,
            )

            // 商品基本資訊
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
            ) {
                Text(
                    text = product.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                // Add to bag / - n +
                Spacer(modifier = Modifier.height(8.dp))
                if (bagQuantity == 0) {
                    // 顯示 "Add to bag" 按鈕
                    Button(
                        onClick = { onAddToBagClick?.invoke() },
                        enabled = onAddToBagClick != null,
                    ) {
                        Text("Add to bag")
                    }
                } else {
                    // 顯示 "- bagQuantity +"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { onDecrementClick?.invoke() },
                            enabled = onDecrementClick != null,
                        ) {
                            Text("-")
                        }
                        Text(
                            bagQuantity.toString(),
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                        IconButton(
                            onClick = { onIncrementClick?.invoke() },
                            enabled = onIncrementClick != null,
                        ) {
                            Text("+")
                        }
                    }
                }
            }

            // 右上角愛心 (Favorites)
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint =
                        if (isFavorite) {
                            androidx.compose.ui.graphics.Color.Red
                        } else {
                            androidx.compose.ui.graphics.Color.Gray
                        },
                )
            }
        }
    }
}
