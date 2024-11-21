package com.example.shoppingapp.repository

//import com.example.shoppingapp.model.Product
//import kotlinx.coroutines.delay
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class ProductRepositoryImpl @Inject constructor() : ProductRepository {
//
//    override suspend fun searchProducts(query: String): List<Product> {
//        // 模拟网络延迟
//        delay(1000)
//
//        // 返回示例数据
//        val allProducts = listOf(
//            Product(id = 1, name = "Product 1"),
//            Product(id = 2, name = "Product 2"),
//            Product(id = 3, name = "Sample Product")
//        )
//
//        return allProducts.filter { it.name.contains(query, ignoreCase = true) }
//    }
//}


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