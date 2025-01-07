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
import com.example.shoppingapp.viewmodel.FavoritesViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    // 在 NavGraph 建立一次 AuthViewModel
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    // 在 NavGraph 建立一次 FavoritesViewModel
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()

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

        // Shop
        composable(Screen.ShopScreen.route) {
            ShopScreen(
                onSearchBarFocused = {
                    navController.navigate(Screen.SuggestionScreen.route) {
                        popUpTo(Screen.ShopScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
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
                favoritesViewModel = favoritesViewModel, // 傳同一個
                onProductClicked = { productId ->
                    navController.navigate(
                        Screen.ProductDetailScreen.createRoute(productId),
                    )
                },
            )
        }

        // SuggestionScreen
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
                favoritesViewModel = favoritesViewModel, // 傳同一個
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
            // 必須顯式傳入 NavGraph scope 裡的 favoritesViewModel
            FavoritesScreen(viewModel = favoritesViewModel)
        }

        // Bag, Account (沒用到 favoritesViewModel 就不用傳)
        composable(Screen.BagScreen.route) { BagScreen() }
        composable(Screen.AccountScreen.route) {
            AccountScreen(authViewModel = authViewModel)
        }
    }
}
