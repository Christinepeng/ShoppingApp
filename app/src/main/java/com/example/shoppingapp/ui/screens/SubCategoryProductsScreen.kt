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
import com.example.shoppingapp.viewmodel.BagViewModel
import com.example.shoppingapp.viewmodel.FavoritesViewModel
import com.example.shoppingapp.viewmodel.SubCategoryProductsViewModel

@Composable
fun SubCategoryProductsScreen(
    mainCategory: String,
    subCategory: String,
    favoritesViewModel: FavoritesViewModel,
    bagViewModel: BagViewModel, // ❶ 新增 BagViewModel
    onProductClicked: (String) -> Unit,
) {
    val viewModel: SubCategoryProductsViewModel = hiltViewModel()
    val productList by viewModel.searchResults.collectAsState()

    // ❷ 一進畫面就查詢
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
                    // ❸ Favorites
                    val isFav = favoritesViewModel.isFavorite(product.id)

                    // ❹ Bag
                    val quantity = bagViewModel.getQuantity(product.id)

                    ProductItem(
                        product = product,
                        // Favorites
                        isFavorite = isFav,
                        onFavoriteClick = {
                            favoritesViewModel.toggleFavorite(product)
                        },
                        // Bag
                        bagQuantity = quantity,
                        onAddToBagClick = { bagViewModel.addToBag(product) },
                        onIncrementClick = { bagViewModel.incrementQuantity(product) },
                        onDecrementClick = { bagViewModel.decrementQuantity(product) },
                        onClick = {
                            onProductClicked(product.id)
                        },
                    )
                }
            }
        }
    }
}
