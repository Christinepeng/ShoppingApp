package com.example.shoppingapp.repository

import com.example.shoppingapp.model.Product
import com.example.shoppingapp.model.ProductDetailResponse
import com.example.shoppingapp.network.ProductApiService
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    override suspend fun searchProducts(query: String): List<Product> {
        val response = apiService.searchProducts(query)
        return response.results
    }

    override suspend fun getProductDetail(productId: String): ProductDetailResponse {
        val detail = apiService.getProductDetail(productId)
        // 获取商品描述（如果需要）
        val descriptionResponse = apiService.getProductDescription(productId)
        return detail.copy(description = descriptionResponse.plain_text)
    }
}