package com.example.shoppingapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shoppingapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
//    object Home : Screen("home", R.string.home, Icons.Default.Home)
//    object Search : Screen("search", R.string.search, Icons.Default.Search)
//    object Profile : Screen("profile", R.string.profile, Icons.Default.Person)
}
