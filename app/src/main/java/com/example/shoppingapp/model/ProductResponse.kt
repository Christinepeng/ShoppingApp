package com.example.shoppingapp.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val results: List<Product>,
)

data class Product(
    val id: String,
    val name: String?,
    val title: String?,
    val price: Double,
    @SerializedName("thumbnail") val thumbnail: String?,
)
