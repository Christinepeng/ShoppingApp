package com.example.shoppingapp.model

data class ProductDetailResponse(
    val id: String,
    val title: String,
    val price: Double,
    val pictures: List<Picture>,
    val description: String?, // 需要额外的 API 获取描述
    // 添加其他需要的字段
)

data class Picture(
    val id: String,
    val url: String
)