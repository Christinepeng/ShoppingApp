package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.viewmodel.SubCategoryProductsViewModel

@Composable
fun SubCategoryProductsScreen(
    mainCategory: String,
    subCategory: String,
    onProductClicked: (String) -> Unit,
) {
    // 與 SearchScreen 類似的流程
    val viewModel: SubCategoryProductsViewModel = hiltViewModel()
    val productList by viewModel.searchResults.collectAsState()

    // 一進畫面就觸發搜尋：把主分類 + 細分分類組成 query (簡單示範)
    LaunchedEffect(mainCategory, subCategory) {
        val combinedQuery = "$mainCategory $subCategory".trim()
        viewModel.searchForSubCategory(combinedQuery)
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text(
            text = "SubCategory Products: $mainCategory > $subCategory",
            modifier = Modifier.padding(8.dp),
        )

        if (productList.isEmpty()) {
            Text(
                text = "No products found for $mainCategory / $subCategory",
                modifier = Modifier.padding(16.dp),
            )
        } else {
            LazyColumn {
                items(productList) { product ->
                    ProductItem(
                        product = product,
                        onClick = {
                            onProductClicked(product.id)
                        },
                    )
                }
            }
        }
    }
}
