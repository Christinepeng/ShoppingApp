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

        private val _shouldFetchSuggestions = MutableStateFlow(true)

        private val _searchSuggestions = MutableStateFlow<List<String>>(emptyList())
        val searchSuggestions: StateFlow<List<String>> = _searchSuggestions.asStateFlow()

        private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
        val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()

        init {
            viewModelScope.launch {
                combine(_query, _shouldFetchSuggestions) { query, shouldFetch ->
                    Pair(query, shouldFetch)
                }.debounce(300)
                    .filter { it.first.isNotEmpty() && it.second }
                    .collect { (query, _) ->
                        try {
                            val results = productRepository.searchProducts(query)
                            val suggestions = results.map { it.title ?: "" }
                            _searchSuggestions.value = suggestions
                            _searchResults.value = emptyList()
                            Log.d("SearchViewModel", "Fetched suggestions for query: $query")
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "Error fetching suggestions: ${e.message}", e)
                            _searchSuggestions.value = emptyList()
                            // 根据需求，可以设置错误状态
                        }
                    }
            }
        }

        fun onQueryChanged(newQuery: String) {
            _query.value = newQuery
            if (newQuery.isEmpty()) {
                // 用户清空输入
                resetState()
            } else {
                _shouldFetchSuggestions.value = true
            }
        }

        fun onSuggestionClicked(suggestion: String) {
            _shouldFetchSuggestions.value = false
            _query.value = suggestion
            _searchSuggestions.value = emptyList()
            onSearchClicked()
        }

        fun onSearchClicked() {
            _shouldFetchSuggestions.value = false
            viewModelScope.launch {
                try {
                    val currentQuery = _query.value
                    val results = productRepository.searchProducts(currentQuery)
                    _searchResults.value = results
                    _searchSuggestions.value = emptyList()
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

        fun resetState() {
            _query.value = ""
            _shouldFetchSuggestions.value = false
            _searchSuggestions.value = emptyList()
            _searchResults.value = emptyList()
        }
    }
