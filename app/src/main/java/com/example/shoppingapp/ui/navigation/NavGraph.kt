package com.example.shoppingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppingapp.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier,
    ) {
        // Home
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Shop
        composable(Screen.ShopScreen.route) {
            ShopScreen(
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.ShopScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Suggestion List
        composable(route = Screen.SuggestionScreen.route) {
            SuggestionScreen(
                onSuggestionClicked = { suggestion ->
                    navController.navigate(Screen.SearchScreen.createRoute(suggestion)) {
                        popUpTo(Screen.SuggestionScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Search Results
        composable(
            route = Screen.SearchScreen.route,
            arguments =
                listOf(
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SearchScreen(
                query = query,
                onProductClicked = { productId ->
                    navController.navigate(Screen.ProductDetailScreen.createRoute(productId))
                },
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Product Detail
        composable(
            route = Screen.ProductDetailScreen.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
            )
        }

        // Favorites, Bag, Account
        composable(Screen.FavoritesScreen.route) { FavoritesScreen() }
        composable(Screen.BagScreen.route) { BagScreen() }
        composable(Screen.AccountScreen.route) { AccountScreen() }
    }
}
