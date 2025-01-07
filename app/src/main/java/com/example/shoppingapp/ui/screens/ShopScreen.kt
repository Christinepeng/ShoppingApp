package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.ShopViewModel

@Composable
fun ShopScreen(
    onSearchBarFocused: () -> Unit,
    onCategoryClick: (String) -> Unit,
) {
    val viewModel: ShopViewModel = hiltViewModel()

    // 定義要顯示的所有分類
    val categories =
        listOf(
            "Women",
            "Men",
            "Beauty",
            "Shoes",
            "Home",
            "Kids, Baby & Toys",
            "Jewelry & Watches",
            "Handbags & Accessories",
            "Bed & Bath",
            "Furniture & Mattresses",
            "Kitchen & Dining",
            "Luggage & Backpacks",
            "Electronics",
            "Sale",
        )

    Column(modifier = Modifier.fillMaxSize()) {
        // 1) 搜尋欄，保持頂端
        SearchContent(
            query = "",
            onQueryChanged = {},
            onSearchClicked = {},
            onImageCaptured = {},
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        // 2) 使用 LazyColumn 顯示分類列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            // 讓每個項目間的間距更明顯
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(categories) { category ->
                // 單一行分類項目 -> 包在 Card (或 Box) 裡
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category) },
                )
            }
        }
    }
}

/**
 * 每個分類項目的「長方形容器」。
 * - 填滿寬度
 * - 高度隨文字，但上下額外預留 padding
 * - 文字點擊範圍 = 整個 Card
 */
@Composable
fun CategoryItem(
    category: String,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        // 你也可以設定圓角 / elevation
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        // 這裡的 padding 就是留給文字的上下空間
        Box(
            modifier =
                Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp),
        ) {
            Text(
                text = category,
                fontSize = 24.sp,
                // style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp)
            )
        }
    }
}
