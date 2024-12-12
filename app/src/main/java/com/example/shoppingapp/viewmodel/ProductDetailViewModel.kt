package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.ProductDetailResponse
import com.example.shoppingapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel
    @Inject
    constructor(
        private val productRepository: ProductRepository,
    ) : ViewModel() {
        private val _productDetail = MutableStateFlow<ProductDetailResponse?>(null)
        val productDetail: StateFlow<ProductDetailResponse?> = _productDetail.asStateFlow()

        fun loadProductDetail(productId: String) {
            viewModelScope.launch {
                try {
                    val detail = productRepository.getProductDetail(productId)
                    _productDetail.value = detail
                    Log.d("ProductDetailViewModel", "Loaded product detail for id: $productId")
                } catch (e: Exception) {
                    Log.e("ProductDetailViewModel", "Error loading product detail: ${e.message}", e)
                    _productDetail.value = null
                    // 根据需求，可以设置错误状态
                }
            }
        }
    }
