package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel
    @Inject
    constructor(
        private val productRepository: ProductRepository,
    ) : ViewModel() {
        private val _suggestions = MutableStateFlow<List<String>>(emptyList())
        val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

        fun fetchSuggestions(query: String) {
            viewModelScope.launch {
                try {
                    val results = productRepository.searchProducts(query)
                    val suggestions = results.mapNotNull { it.title }
                    _suggestions.value = suggestions
                } catch (e: Exception) {
                    // 记录错误日志
                    // 这里可以使用 Log.e 或其他日志工具
                    _suggestions.value = emptyList()
                }
            }
        }
    }
