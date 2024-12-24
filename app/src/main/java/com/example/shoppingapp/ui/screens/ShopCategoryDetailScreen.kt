package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShopCategoryDetailScreen(categoryName: String) {
    // 依主分類名稱 => 找到對應細分分類
    val subcategoriesMap =
        mapOf(
            "Women" to
                listOf(
                    "All Women",
                    "Sale & Clearance",
                    "Women's Clothing",
                    "Plus Size Clothing",
                    "Petite Clothing",
                    "Juniors' Clothing",
                    "Maternity Clothing",
                    "Shoes",
                    "Handbags & Wallets",
                    "Accessories & Sunglasses",
                    "Jewelry & Watches",
                    "Beauty",
                    "Brands",
                ),
            "Men" to
                listOf(
                    "All Men",
                    "Sale & Clearance",
                    "Men's Clothing",
                    "Big & Tall",
                    "Cologne & Grooming",
                    "Accessories & Sunglasses",
                    "Jewelry & Watches",
                    "Sports Fan Shop",
                    "Brands",
                ),
            "Beauty" to
                listOf(
                    "All Beauty",
                    "Gift Sets",
                    "Makeup",
                    "Skin Care",
                    "Hair Care",
                    "Fragrance",
                    "Tools & Accessories",
                    "Bath & Body",
                    "Beauty Brands",
                ),
            "Shoes" to
                listOf(
                    "All Shoes",
                    "Sale & Clearance",
                    "Men's Shoes",
                    "Women's Shoes",
                    "Kids' Shoes",
                    "Brands",
                ),
            "Home" to
                listOf(
                    "All Home",
                    "Sale & Clearance",
                    "Bedding",
                    "Bath",
                    "Kitchen",
                    "Dining",
                    "Luggage",
                    "Furniture",
                    "Mattresses",
                    "Rugs",
                    "Home Decor",
                    "Cleaning & Organization",
                    "Healthy Living",
                    "Electronics",
                    "Sporting & Outdoor Equipment",
                    "Kids & Baby Room",
                ),
            "Kids, Baby & Toys" to
                listOf(
                    "All Kids, Baby & Toys",
                    "Sale & Clearance",
                    "All Toys",
                    "Boys' Clothing (4-20)",
                    "Girls' Clothing (4-16)",
                    "Toddler Boy Clothing (2T-5T)",
                    "Toddler Girl Clothing (2T-5T)",
                    "Baby Boy Clothing (0-24M)",
                    "Baby Girl Clothing (0-24M)",
                    "Shoes",
                    "Accessories",
                    "Kids' Clothing",
                    "Brands",
                ),
            "Jewelry & Watches" to
                listOf(
                    "All Jewelry & Watches",
                    "Jewelry Gifts",
                    "Jewelry",
                    "Watches",
                    "Men's Jewelry",
                    "Kids' Jewelry",
                    "Wedding & Engagement Jewelry",
                ),
            "Handbags & Accessories" to
                listOf(
                    "All Handbags & Accessories",
                    "Handbags Sale & Clearance",
                    "Accessories Sale & Clearance",
                    "Handbags",
                    "Wallets",
                    "Belts",
                    "Hats",
                    "Sunglasses",
                    "Scarves & Wraps",
                    "Gloves",
                    "Eyeglasses",
                    "Hair Accessories",
                    "Brands",
                ),
            "Bed & Bath" to
                listOf(
                    "All Bed & Bath",
                    "Sale & Clearance",
                    "Bedding",
                    "Bath",
                    "Bed Sheets & Pillowcases",
                    "Comforters & Sets",
                    "Blankets & Throws",
                    "Quilts & Bedspreads",
                    "Pillows",
                    "Bedding Essentials",
                    "Luxury Bedding",
                    "Coverlets",
                    "Pillow Protectors",
                    "Mattress Covers",
                    "Bed Skirts",
                    "Brands",
                ),
            "Furniture & Mattresses" to
                listOf(
                    "All Furniture & Mattresses",
                    "Sale & Clearance",
                    "Living Room Furniture",
                    "Bedroom Furniture",
                    "Dining Room & Kitchen Furniture",
                    "Mattresses",
                    "Outdoor & Patio Furniture",
                    "Entertainment & Game Room",
                    "Home Office",
                    "Entryway",
                    "Kids & Baby Room",
                    "Small Space Furniture",
                ),
            "Kitchen & Dining" to
                listOf(
                    "All Kitchen & Dining",
                    "Sale & Clearance",
                    "All Kitchen",
                    "All Dining",
                    "Coffee & Espresso Makers",
                    "Cookware",
                    "Small Appliances",
                    "Bakeware",
                    "Bar & Wine Accessories",
                    "Dinnerware",
                    "Cutlery & Knife Sets",
                    "Glassware & Drinkware",
                    "Flatware & Silverware",
                    "Kitchen Gadgets & Utensils",
                    "Kitchen Storage & Organization",
                    "Serveware",
                ),
            "Luggage & Backpacks" to
                listOf(
                    "All Luggage & Backpacks",
                    "Sale & Clearance",
                    "Luggage",
                    "Carry-On Luggage",
                    "Checked Luggage",
                    "Luggage Sets",
                    "Backpacks",
                    "Travel Bags",
                    "Travel Accessories",
                    "Brands",
                ),
            "Electronics" to
                listOf(
                    "All Electronics",
                    "Sale & Clearance",
                    "TVs & Home Theater",
                    "Video Games & Consoles",
                    "Speakers & Audio",
                    "Headphones & Headsets",
                    "Computers",
                    "Cameras & Photography",
                    "Smart Home",
                    "Kids Tech & Electronics",
                    "Cell Phone Accessories",
                    "Cell Phones",
                    "Computer Accessories",
                ),
        )

    // 取得對應的細分列表；若沒有，就給空List
    val subcategories = subcategoriesMap[categoryName] ?: emptyList()

    // 若需要進一步點擊subCategory再做下一步(顯示商品列表等)，可另外設計 onSubCategoryClick
    // 這裡先示範靜態顯示
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Category: $categoryName",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (subcategories.isEmpty()) {
            // 如果該主分類沒在 map 裡，顯示提示
            Text("No subcategories found for $categoryName.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(subcategories) { subCat ->
                    SubCategoryItem(subCat)
                }
            }
        }
    }
}

// 單一細分分類項目 (可點擊或直接顯示)
@Composable
fun SubCategoryItem(subCategoryName: String) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    // TODO: 未來可做進一步導覽
                },
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Box(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Text(text = subCategoryName, style = MaterialTheme.typography.titleMedium)
        }
    }
}
