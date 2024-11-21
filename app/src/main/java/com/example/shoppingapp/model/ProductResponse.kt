package com.example.shoppingapp.model

data class ProductResponse(
    val results: List<Product>
)

data class Product(
    val id: String,
    val name: String,
    val title: String,
    val price: Double,
    val thumbnail: String
    // 添加其他需要的字段
)