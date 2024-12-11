package com.example.shoppingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.model.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
    ) {
        val imageUrl =
            product.thumbnail?.takeIf { it.isNotEmpty() }
                ?: "https://default-thumbnail-url.com/image.png" // 提供默认图片

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
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
        ) {
            Text(
                text = product.title ?: "No Title",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "价格：$${product.price}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
