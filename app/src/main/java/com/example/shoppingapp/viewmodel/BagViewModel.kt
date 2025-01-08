package com.example.shoppingapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 購物車項目 (BagItem)，內含商品與數量
 */
data class BagItem(
    val product: Product,
    val quantity: Int,
)

@HiltViewModel
class BagViewModel
    @Inject
    constructor() : ViewModel() {
        private val _bagItems = mutableStateListOf<BagItem>()
        val bagItems: List<BagItem> get() = _bagItems

        /**
         * 取得某商品在購物車中的數量
         */
        fun getQuantity(productId: String): Int = _bagItems.find { it.product.id == productId }?.quantity ?: 0

        /**
         * 新增商品到購物車：
         * - 若購物車原本沒有該商品 -> quantity=1
         * - 若已存在 -> 數量+1
         */
        fun addToBag(product: Product) {
            val index = _bagItems.indexOfFirst { it.product.id == product.id }
            if (index == -1) {
                // 不存在，直接新增
                _bagItems.add(BagItem(product, 1))
            } else {
                // 已存在 -> copy +1
                val oldItem = _bagItems[index]
                val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
                _bagItems[index] = newItem
            }
        }

        /**
         * 數量 +1
         */
        fun incrementQuantity(product: Product) {
            val index = _bagItems.indexOfFirst { it.product.id == product.id }
            if (index == -1) {
                // 原本沒有 -> 新增 quantity=1
                _bagItems.add(BagItem(product, 1))
            } else {
                // 已存在 -> copy +1
                val oldItem = _bagItems[index]
                val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
                _bagItems[index] = newItem
            }
        }

        /**
         * 數量 -1，若結果為 0 就從購物車移除
         */
        fun decrementQuantity(product: Product) {
            val index = _bagItems.indexOfFirst { it.product.id == product.id }
            if (index == -1) return // 不在購物車，無動作

            val oldItem = _bagItems[index]
            val newQty = oldItem.quantity - 1
            if (newQty <= 0) {
                _bagItems.removeAt(index)
            } else {
                val newItem = oldItem.copy(quantity = newQty)
                _bagItems[index] = newItem
            }
        }

        /**
         * 從購物車整筆移除
         */
        fun removeProduct(productId: String) {
            _bagItems.removeAll { it.product.id == productId }
        }
    }
