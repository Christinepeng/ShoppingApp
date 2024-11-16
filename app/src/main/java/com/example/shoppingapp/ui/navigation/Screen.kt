package com.example.shoppingapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shoppingapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Shop : Screen("shop", R.string.shop, Icons.Default.Search)
    object Favorites : Screen("favorites", R.string.favorites, Icons.Default.Favorite)
    object Bag : Screen("bag", R.string.bag, Icons.Default.ShoppingCart)
    object Account : Screen("account", R.string.account, Icons.Default.Person)
}
