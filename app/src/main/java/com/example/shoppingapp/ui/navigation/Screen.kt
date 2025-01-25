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

    object ShopCategoryDetailScreen : Screen("ShopCategoryDetailScreen") {
        fun createRoute(categoryName: String) = "ShopCategoryDetailScreen/$categoryName"
    }

    object FavoritesScreen : Screen("favoritesScreen", R.string.favorites, Icons.Default.Favorite)

    object BagScreen : Screen("bagScreen", R.string.bag, Icons.Default.ShoppingCart)

    object AccountScreen : Screen("accountScreen", R.string.account, Icons.Default.Person)

    // SubCategoryProductsScreen，帶兩個參數 (主分類、細分分類)
    object SubCategoryProductsScreen : Screen("subCategoryProductsScreen?main={main}&sub={sub}") {
        fun createRoute(
            mainCategory: String,
            subCategory: String,
        ): String = "subCategoryProductsScreen?main=$mainCategory&sub=$subCategory"
    }

    // Account tab
    object PurchasesReturnsScreen : Screen("purchasesReturnsScreen")

    object StarRewardsScreen : Screen("starRewardsScreen")

    object GetHelpFeedbackScreen : Screen("helpFeedbackScreen")

    object WalletScreen : Screen("walletScreen")

    object ProfileScreen : Screen("profileScreen")

    object PrivacySettingScreen : Screen("privacySettingScreen")
}
