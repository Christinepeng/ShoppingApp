package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoryProductsViewModel
    @Inject
    constructor(
        private val productRepository: ProductRepository,
    ) : ViewModel() {
        private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
        val searchResults: StateFlow<List<Product>> get() = _searchResults

        /**
         * 以「主分類 + 細分分類」當作 query 去呼叫 searchProducts
         */
        fun searchForSubCategory(query: String) {
            viewModelScope.launch {
                try {
                    val products = productRepository.searchProducts(query)
                    _searchResults.value = products
                } catch (e: Exception) {
                    // 失敗時，可記錄錯誤或提示使用者
                    _searchResults.value = emptyList()
                }
            }
        }
    }
