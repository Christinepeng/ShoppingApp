package com.example.shoppingapp.ui.components

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun CameraHandler(
    onImageCaptured: (Bitmap) -> Unit,
    content: @Composable (launchCamera: () -> Unit) -> Unit
) {
    val context = LocalContext.current

    // 用于保存拍摄的图像（可选）
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    // 启动相机的 Launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            capturedImage = bitmap
            onImageCaptured(bitmap)
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

    // 定义启动相机的方法
    val launchCamera = {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // 将启动相机的方法传递给子组件
    content(launchCamera)
}
