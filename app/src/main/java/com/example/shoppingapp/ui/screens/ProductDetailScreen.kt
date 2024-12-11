package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: String?,
    onBack: () -> Unit,
) {
    val viewModel: ProductDetailViewModel = hiltViewModel()
    val productDetail by viewModel.productDetail.collectAsState()

    // 在屏幕首次加载时触发数据加载
    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProductDetail(productId)
        }
    }

    // UI展示逻辑保持不变
    if (productDetail != null) {
        val product = productDetail!!
        Column(modifier = Modifier.padding(16.dp)) {
            // 显示商品图片
            if (product.pictures.isNotEmpty()) {
                val imageUrl = product.pictures.firstOrNull()?.url ?: product.thumbnail
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = product.title ?: "Product Image",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // 显示商品标题
            Text(text = product.title ?: "No Title", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // 显示商品价格
            Text(text = "价格：$${product.price}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // 显示商品描述
            Text(text = product.description ?: "暂无描述", style = MaterialTheme.typography.bodyMedium)
            // 您可以添加更多的商品信息展示
        }
    } else {
        // 显示加载状态或占位符
        Text(text = "加载中...")
    }
}
