package com.example.shoppingapp.network

import com.example.shoppingapp.model.ProductDescriptionResponse
import com.example.shoppingapp.model.ProductDetailResponse
import com.example.shoppingapp.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("sites/MLA/search")
    suspend fun searchProducts(
        @Query("q") query: String,
    ): ProductResponse

    @GET("items/{id}")
    suspend fun getProductDetail(
        @Path("id") productId: String,
    ): ProductDetailResponse

    // 添加获取商品描述的方法
    @GET("items/{id}/description")
    suspend fun getProductDescription(
        @Path("id") productId: String,
    ): ProductDescriptionResponse
}
