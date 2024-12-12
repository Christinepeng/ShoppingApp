package com.example.shoppingapp.repository

import com.example.shoppingapp.model.Product
import com.example.shoppingapp.model.ProductDetailResponse
import com.example.shoppingapp.network.ProductApiService
import javax.inject.Inject

class ProductRepositoryImpl
    @Inject
    constructor(
        private val apiService: ProductApiService,
    ) : ProductRepository {
        override suspend fun searchProducts(query: String): List<Product> {
            val response = apiService.searchProducts(query)
            // 如果 response.results 是可空的，使用 ?: emptyList()
            val results = response.results ?: emptyList()

            return results.map { product ->
                val originalThumbnail = product.thumbnail
                val updatedThumbnail =
                    if (originalThumbnail?.startsWith("http://") == true) {
                        originalThumbnail.replace("http://", "https://")
                    } else {
                        originalThumbnail ?: "https://default-thumbnail-url.com/image.png"
                    }

                // 对需要非空的字段提供默认值
                val safeName = product.name ?: "Unknown Name"
                val safeTitle = product.title ?: "No Title"

                product.copy(
                    name = safeName,
                    title = safeTitle,
                    thumbnail = updatedThumbnail,
                )
            }
        }

        override suspend fun getProductDetail(productId: String): ProductDetailResponse {
            val detail = apiService.getProductDetail(productId)
            val descriptionResponse = apiService.getProductDescription(productId)

            // 对 pictures 中 url 进行 http -> https 替换
            val updatedPictures =
                detail.pictures.orEmpty().map { picture ->
                    val updatedUrl =
                        if (picture.url.startsWith("http://")) {
                            picture.url.replace("http://", "https://")
                        } else {
                            picture.url
                        }
                    picture.copy(url = updatedUrl)
                }

            return detail.copy(
                description = descriptionResponse.plain_text,
                pictures = updatedPictures,
            )
        }
    }
