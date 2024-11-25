package com.example.shoppingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppingapp.ui.screens.AccountScreen
import com.example.shoppingapp.ui.screens.BagScreen
import com.example.shoppingapp.ui.screens.FavoritesScreen
import com.example.shoppingapp.ui.screens.HomeScreen
import com.example.shoppingapp.ui.screens.ProductDetailScreen
import com.example.shoppingapp.ui.screens.ShopScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(
            route = Screen.ProductDetail.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(productId = productId)
        }
        composable(Screen.Shop.route) { ShopScreen(navController) }
        composable(Screen.Favorites.route) { FavoritesScreen() }
        composable(Screen.Bag.route) { BagScreen() }
        composable(Screen.Account.route) { AccountScreen() }
    }
}
