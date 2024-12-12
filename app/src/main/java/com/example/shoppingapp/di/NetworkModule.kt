package com.example.shoppingapp.di

import com.example.shoppingapp.network.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.mercadolibre.com/") // MercadoLibre API 的基本 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideProductApiService(retrofit: Retrofit): ProductApiService = retrofit.create(ProductApiService::class.java)
}
