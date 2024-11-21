package com.example.shoppingapp.repository

import com.example.shoppingapp.model.Product
import com.example.shoppingapp.network.ProductApiService
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    override suspend fun searchProducts(query: String): List<Product> {
        val response = apiService.searchProducts(query)
        return response.results
    }
}