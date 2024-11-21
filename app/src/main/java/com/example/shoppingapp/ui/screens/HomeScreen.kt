package com.example.shoppingapp.ui.screens

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp.ui.components.ProductItem
import com.example.shoppingapp.ui.components.SearchBar
import com.example.shoppingapp.ui.navigation.Screen
import com.example.shoppingapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val searchSuggestions by viewModel.searchSuggestions.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    val context = LocalContext.current

    // 用于保存拍摄的图像
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    // 启动相机的 Launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            capturedImage = bitmap
            // 在这里处理图像，例如扫描条码
            // 您可以将图像传递给 ViewModel 进行进一步处理
            viewModel.onImageCaptured(bitmap)
        }
    }

    // 请求相机权限的 Launcher
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 权限已授予，启动相机
            cameraLauncher.launch()
        } else {
            // 权限被拒绝，显示提示
            Toast.makeText(context, "相机权限被拒绝", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        SearchBar(
            query = query,
            onQueryChanged = { viewModel.onQueryChanged(it) },
            onSearchClicked = { viewModel.onSearchClicked() },
            onBarcodeScanClicked = { // 请求相机权限
                permissionLauncher.launch(Manifest.permission.CAMERA) }
        )
        // 显示搜索建议
        if (searchSuggestions.isNotEmpty()) {
            LazyColumn {
                items(searchSuggestions) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onSuggestionClicked(suggestion)
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        // 显示搜索结果
        if (searchResults.isNotEmpty()) {
            LazyColumn {
                items(searchResults) { product ->
                    ProductItem(product = product, onClick = {
                        navController.navigate(Screen.ProductDetail.route + "/${product.id}")
                    })
                }
            }
        }

//        // 可选：显示拍摄的图像
//        capturedImage?.let { bitmap ->
//            Image(
//                bitmap = bitmap.asImageBitmap(),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(8.dp)
//            )
//        }

//        LazyColumn {
//            items(searchResults) { product ->
//                Text(text = product.name)
//            }
//        }
    }
}