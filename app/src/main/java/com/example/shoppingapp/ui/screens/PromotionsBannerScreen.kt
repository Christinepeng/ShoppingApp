package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R

@Composable
fun PromotionsBannerScreen() {
    // 不要再使用 verticalScroll，否則與上層衝突
    Image(
        painter = painterResource(id = R.drawable.promotions_image),
        contentDescription = "Promotions Banner",
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(1500.dp),
    )
}
