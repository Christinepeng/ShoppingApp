package com.example.shoppingapp.repository

import com.example.shoppingapp.model.Product


interface ProductRepository {
    suspend fun searchProducts(query: String): List<Product>
}