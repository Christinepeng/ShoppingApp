package com.example.shoppingapp.model

data class ProductDetailResponse(
    val id: String,
    val title: String,
    val price: Double,
    val pictures: List<Picture>,
    val description: String?,
    val thumbnail: String?,
)

data class Picture(
    val id: String,
    val url: String,
)
