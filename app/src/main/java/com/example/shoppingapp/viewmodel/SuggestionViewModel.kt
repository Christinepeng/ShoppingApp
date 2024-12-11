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
        private val _query = MutableStateFlow("")
        val query: StateFlow<String> = _query.asStateFlow()

        private val _suggestions = MutableStateFlow<List<String>>(emptyList())
        val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

        fun fetchSuggestions() {
            viewModelScope.launch {
                _query
                    .debounce(300) // 等待300毫秒後才處理最新的query
                    .filter { it.isNotEmpty() }
                    .distinctUntilChanged() // 僅當query變化時才觸發
                    .collectLatest { query ->
                        try {
                            val results = productRepository.searchProducts(query)
                            val suggestions = results.mapNotNull { it.title }
                            _suggestions.value = suggestions
                        } catch (e: Exception) {
                            _suggestions.value = emptyList()
                        }
                    }
            }
        }

        fun onQueryChanged(newQuery: String) {
            _query.value = newQuery
        }
    }
