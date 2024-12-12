package com.example.shoppingapp.repository

import com.example.shoppingapp.model.Product
import com.example.shoppingapp.model.ProductDetailResponse


interface ProductRepository {
    suspend fun searchProducts(query: String): List<Product>
    suspend fun getProductDetail(productId: String): ProductDetailResponse

}