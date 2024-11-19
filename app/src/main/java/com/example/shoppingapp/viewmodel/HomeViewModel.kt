package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
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
            val currentQuery = _query.value
            Log.d("HomeViewModel", "Searching for query: $currentQuery")
            val results = productRepository.searchProducts(currentQuery)
            Log.d("HomeViewModel", "Search results: $results")
            _searchResults.value = results
        }
    }

    fun onBarcodeScanClicked() {
        // 处理条码扫描的逻辑，暂时留空
    }
}