package com.example.shoppingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
        Image(
            painter = rememberAsyncImagePainter(product.thumbnail),
            contentDescription = product.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = product.title ?: "No Title")
            Text(text = "$${product.price}")
        }
    }
}
