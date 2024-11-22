package com.example.shoppingapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.ProductDetailResponse
import com.example.shoppingapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetail = MutableStateFlow<ProductDetailResponse?>(null)
    val productDetail: StateFlow<ProductDetailResponse?> = _productDetail.asStateFlow()

    init {
        val productId = savedStateHandle.get<String>("productId")
        if (productId != null) {
            viewModelScope.launch {
                val detail = productRepository.getProductDetail(productId)
                _productDetail.value = detail
            }
        }
    }
}
