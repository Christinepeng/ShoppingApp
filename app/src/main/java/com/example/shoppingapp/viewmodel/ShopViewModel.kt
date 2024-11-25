package com.example.shoppingapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.ProductRepository
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository
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
            }
                .debounce(300)
                .filter { it.first.isNotEmpty() && it.second }
                .collect { (query, _) ->
                    // 获取搜索建议
                    val results = productRepository.searchProducts(query)
                    val suggestions = results.map { it.title }
                    _searchSuggestions.value = suggestions
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        _shouldFetchSuggestions.value = true // 用户手动输入，允许获取推荐列表
    }

    fun onSuggestionClicked(suggestion: String) {
        _shouldFetchSuggestions.value = false // 禁止获取推荐列表
        _query.value = suggestion
        _searchSuggestions.value = emptyList() // 清空推荐列表
        onSearchClicked()
    }

    fun onSearchClicked() {
        _shouldFetchSuggestions.value = false // 禁止获取推荐列表
        viewModelScope.launch {
            val results = productRepository.searchProducts(_query.value)
            _searchResults.value = results
            _searchSuggestions.value = emptyList() // 清空推荐列表
        }
    }

    fun onImageCaptured(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                // 处理扫描结果
                for (barcode in barcodes) {
                    val rawValue = barcode.rawValue
                    // 更新搜索查询或执行其他操作
                    _query.value = rawValue ?: ""
                    onSearchClicked()
                }
            }
            .addOnFailureListener { e ->
                // 处理错误
            }
    }

    fun resetState() {
        _query.value = ""
        _shouldFetchSuggestions.value = false
        _searchSuggestions.value = emptyList()
        _searchResults.value = emptyList()
    }

}
