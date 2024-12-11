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
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onShowResults = { query ->
                    // 从 Home 进入搜索结果时，保留 Home 在栈中
                    navController.navigate(Screen.SearchResults.createRoute(query)) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProductClicked = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onShowSuggestions = { query ->
                    navController.navigate(Screen.SuggestionList.createRoute(query)) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Shop
        composable(Screen.Shop.route) {
            ShopScreen(
                onShowResults = { query ->
                    // 从 Shop 进入搜索结果时，保留 Shop 在栈中
                    navController.navigate(Screen.SearchResults.createRoute(query)) {
                        popUpTo(Screen.Shop.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProductClicked = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onShowSuggestions = { query ->
                    navController.navigate(Screen.SuggestionList.createRoute(query)) {
                        popUpTo(Screen.Shop.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // Suggestion List
        composable(
            route = Screen.SuggestionList.route,
            arguments =
                listOf(
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SuggestionListScreen(
                query = query,
                onSuggestionClicked = { suggestion ->
                    navController.navigate(Screen.SearchResults.createRoute(suggestion)) {
                        popUpTo(Screen.SuggestionList.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }

        // Search Results
        composable(
            route = Screen.SearchResults.route,
            arguments =
                listOf(
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SearchResultsScreen(
                query = query,
                onProductClicked = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onBack = { navController.popBackStack() },
            )
        }

        // Product Detail
        composable(
            route = Screen.ProductDetail.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
            )
        }

        // Favorites, Bag, Account
        composable(Screen.Favorites.route) { FavoritesScreen() }
        composable(Screen.Bag.route) { BagScreen() }
        composable(Screen.Account.route) { AccountScreen() }
    }
}
