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

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(), // 在NavGraph建立一次
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
                onNavigateToAuth = {
                    navController.navigate(Screen.AuthScreen.route)
                },
                authViewModel = authViewModel, // 傳遞同一個ViewModel
            )
        }

        // Auth Screen
        composable(Screen.AuthScreen.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.popBackStack() // 返回上一頁
                },
                authViewModel = authViewModel, // 使用同一個ViewModel
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
                // 加入 onCategoryClick，點擊分類後跳到 ShopCategoryDetailScreen
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
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Unknown Category"
            ShopCategoryDetailScreen(
                categoryName = categoryName,
                // lambda，點擊「細分分類」後跳去 SubCategoryProductsScreen
                onSubCategoryClick = { subCat ->
                    navController.navigate(
                        Screen.SubCategoryProductsScreen.createRoute(categoryName, subCat),
                    )
                },
            )
        }

        // SubCategoryProductsScreen composable
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

            // 顯示該細分分類對應的商品列表
            SubCategoryProductsScreen(
                mainCategory = main,
                subCategory = sub,
                onProductClicked = { productId ->
                    // 與 SearchScreen 一樣，點擊商品 -> 前往商品詳情
                    navController.navigate(Screen.ProductDetailScreen.createRoute(productId))
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
        composable(Screen.AccountScreen.route) {
            AccountScreen(
                authViewModel = authViewModel, // 將 AuthViewModel 傳入 AccountScreen
            )
        }
    }
}
