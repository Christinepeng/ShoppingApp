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
import com.example.shoppingapp.viewmodel.FavoritesViewModel
import com.example.shoppingapp.viewmodel.SubCategoryProductsViewModel

@Composable
fun SubCategoryProductsScreen(
    mainCategory: String,
    subCategory: String,
    favoritesViewModel: FavoritesViewModel, // <-- 這裡改成必傳
    onProductClicked: (String) -> Unit,
) {
    // 只使用 SubCategoryProductsViewModel 做搜尋，不再呼叫 favoritesViewModel = hiltViewModel()
    val viewModel: SubCategoryProductsViewModel = hiltViewModel()
    val productList by viewModel.searchResults.collectAsState()

    // 一進畫面就觸發搜尋
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
                    val isFav = favoritesViewModel.isFavorite(product.id)
                    ProductItem(
                        product = product,
                        isFavorite = isFav,
                        onFavoriteClick = {
                            favoritesViewModel.toggleFavorite(product)
                        },
                        onClick = {
                            onProductClicked(product.id)
                        },
                    )
                }
            }
        }
    }
}
