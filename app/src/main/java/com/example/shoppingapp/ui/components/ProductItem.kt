package com.example.shoppingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.model.Product

@Composable
fun ProductItem(
    product: Product,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() } // 整個區塊可點選進入詳情
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

            Column(
                modifier =
                    Modifier
                        .weight(1f) // 使文字區域占用剩餘空間
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
            }

            // 右上角的愛心按鈕
            IconButton(
                onClick = onFavoriteClick,
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray,
                )
            }
        }
    }
}
