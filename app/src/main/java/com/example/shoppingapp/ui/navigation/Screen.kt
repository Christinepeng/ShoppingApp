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
    object Home : Screen("home", R.string.home, Icons.Default.Home)

    object Shop : Screen("shop", R.string.shop, Icons.Default.Search)

    object SuggestionList : Screen("suggestionList?query={query}") {
        fun createRoute(query: String) = "suggestionList?query=$query"
    }

    object SearchResults : Screen("searchResults?query={query}") {
        fun createRoute(query: String) = "searchResults?query=$query"
    }

    object ProductDetail : Screen("productDetail") {
        fun createRoute(productId: String) = "productDetail/$productId"
    }

    object Favorites : Screen("favorites", R.string.favorites, Icons.Default.Favorite)

    object Bag : Screen("bag", R.string.bag, Icons.Default.ShoppingCart)

    object Account : Screen("account", R.string.account, Icons.Default.Person)
}
