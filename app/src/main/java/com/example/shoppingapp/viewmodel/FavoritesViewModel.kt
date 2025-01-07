package com.example.shoppingapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
    @Inject
    constructor() : ViewModel() {
        // 用來存放使用者加入最愛的商品列表
        private val _favoriteProducts = mutableStateListOf<Product>()
        val favoriteProducts: List<Product> get() = _favoriteProducts

        /**
         * 判斷某個商品是否在最愛清單中
         */
        fun isFavorite(productId: String): Boolean = _favoriteProducts.any { it.id == productId }

        /**
         * 加入最愛 (如果已在清單裡，則不重複加入)
         */
        fun addToFavorites(product: Product) {
            if (!isFavorite(product.id)) {
                _favoriteProducts.add(product)
            }
        }

        /**
         * 從最愛移除
         */
        fun removeFromFavorites(productId: String) {
            _favoriteProducts.removeAll { it.id == productId }
        }

        /**
         * 切換最愛狀態：若原本是最愛就移除，否則加入
         */
        fun toggleFavorite(product: Product) {
            if (isFavorite(product.id)) {
                removeFromFavorites(product.id)
            } else {
                addToFavorites(product)
            }
        }
    }
