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


//    fun onBarcodeScanClicked() {
//        // 处理条码扫描的逻辑，暂时留空
//    }
}