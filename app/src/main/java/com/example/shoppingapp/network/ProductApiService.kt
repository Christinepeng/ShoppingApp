package com.example.shoppingapp.network

import com.example.shoppingapp.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("sites/MLA/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductResponse
}