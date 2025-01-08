package com.example.shoppingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppingapp.ui.screens.*
import com.example.shoppingapp.viewmodel.AuthViewModel
import com.example.shoppingapp.viewmodel.BagViewModel
import com.example.shoppingapp.viewmodel.FavoritesViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(), // 單例
) {
    // FavoritesViewModel -> 管理我的最愛
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    // BagViewModel -> 管理購物車
    val bagViewModel: BagViewModel = hiltViewModel()

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
                onNavigateToAuth = {
                    navController.navigate(Screen.AuthScreen.route)
                },
                authViewModel = authViewModel,
            )
        }

        // Auth Screen
        composable(Screen.AuthScreen.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.popBackStack()
                },
                authViewModel = authViewModel,
            )
        }

        // Shop Screen
        composable(Screen.ShopScreen.route) {
            ShopScreen(
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.ShopScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                // ...
                onCategoryClick = { category ->
                    navController.navigate(
                        Screen.ShopCategoryDetailScreen.createRoute(category),
                    )
                },
            )
        }

        // ShopCategoryDetailScreen
        composable(
            route = Screen.ShopCategoryDetailScreen.route + "/{categoryName}",
        ) { backStackEntry ->
            val categoryName =
                backStackEntry.arguments?.getString("categoryName") ?: "Unknown Category"

            ShopCategoryDetailScreen(
                categoryName = categoryName,
                onSubCategoryClick = { subCat ->
                    navController.navigate(
                        Screen.SubCategoryProductsScreen.createRoute(categoryName, subCat),
                    )
                },
            )
        }

        // SubCategoryProductsScreen
        composable(
            route = Screen.SubCategoryProductsScreen.route,
            arguments =
                listOf(
                    navArgument("main") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("sub") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
        ) { backStackEntry ->
            val main = backStackEntry.arguments?.getString("main") ?: ""
            val sub = backStackEntry.arguments?.getString("sub") ?: ""

            SubCategoryProductsScreen(
                mainCategory = main,
                subCategory = sub,
                favoritesViewModel = favoritesViewModel,
                bagViewModel = bagViewModel,
                onProductClicked = { productId ->
                    navController.navigate(
                        Screen.ProductDetailScreen.createRoute(productId),
                    )
                },
            )
        }

        // Suggestion List
        composable(Screen.SuggestionScreen.route) {
            SuggestionScreen(
                onSuggestionClicked = { suggestion ->
                    navController.navigate(Screen.SearchScreen.createRoute(suggestion)) {
                        popUpTo(Screen.SuggestionScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        // SearchScreen
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
                favoritesViewModel = favoritesViewModel,
                bagViewModel = bagViewModel,
                onProductClicked = { productId ->
                    navController.navigate(
                        Screen.ProductDetailScreen.createRoute(productId),
                    )
                },
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }

        // ProductDetailScreen
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

        // Favorites
        composable(Screen.FavoritesScreen.route) {
            FavoritesScreen(viewModel = favoritesViewModel)
        }

        // Bag
        composable(Screen.BagScreen.route) {
            // 直接傳同一個 bagViewModel
            BagScreen(
                bagViewModel = bagViewModel,
            )
        }

        // Account ...
        composable(Screen.AccountScreen.route) {
            AccountScreen(
                authViewModel = authViewModel,
            )
        }
    }
}
