package com.example.shoppingapp.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.ProductRepository
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val productRepository: ProductRepository,
    ) : ViewModel() {
        private val _query = MutableStateFlow("")
        val query: StateFlow<String> = _query.asStateFlow()

        private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
        val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()

        fun onQueryChanged(newQuery: String) {
            _query.value = newQuery
        }

        fun onSearchClicked() {
            viewModelScope.launch {
                try {
                    val currentQuery = _query.value
                    val results = productRepository.searchProducts(currentQuery)
                    _searchResults.value = results
                    Log.d("SearchViewModel", "Fetched search results for query: $currentQuery")
                } catch (e: Exception) {
                    Log.e("SearchViewModel", "Error fetching search results: ${e.message}", e)
                    _searchResults.value = emptyList()
                    // 根据需求，可以设置错误状态
                }
            }
        }

        fun onImageCaptured(bitmap: Bitmap) {
            val image = InputImage.fromBitmap(bitmap, 0)
            val scanner = BarcodeScanning.getClient()
            viewModelScope.launch {
                try {
                    val barcodes = scanner.process(image).await()
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        _query.value = rawValue ?: ""
                        onSearchClicked()
                    }
                } catch (e: Exception) {
                    Log.e("SearchViewModel", "Error processing barcode: ${e.message}", e)
                    // handle error if needed
                }
            }
        }
    }
