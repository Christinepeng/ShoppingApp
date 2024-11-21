package com.example.shoppingapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductDetailScreen(productId: String?) {
    // 根据 productId 获取商品详情，并显示
    Text(text = "商品详情：$productId")
}