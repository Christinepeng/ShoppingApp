package com.example.shoppingapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shoppingapp.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int? = null,
    val icon: ImageVector? = null,
) {
    object HomeScreen : Screen("homeScreen", R.string.home, Icons.Default.Home)

    object AuthScreen : Screen("authScreen")

    object ShopScreen : Screen("shopScreen", R.string.shop, Icons.Default.Search)

    object SuggestionScreen : Screen("suggestionScreen")

    object SearchScreen : Screen("searchResultsScreen?query={query}") {
        fun createRoute(query: String) = "searchResultsScreen?query=$query"
    }

    object ProductDetailScreen : Screen("productDetailScreen") {
        fun createRoute(productId: String) = "productDetailScreen/$productId"
    }

    object FavoritesScreen : Screen("favoritesScreen", R.string.favorites, Icons.Default.Favorite)

    object BagScreen : Screen("bagScreen", R.string.bag, Icons.Default.ShoppingCart)

    object AccountScreen : Screen("accountScreen", R.string.account, Icons.Default.Person)
}
