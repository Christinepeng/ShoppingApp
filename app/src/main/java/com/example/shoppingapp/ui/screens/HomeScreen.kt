package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shoppingapp.R
import com.example.shoppingapp.ui.components.SearchContent
import com.example.shoppingapp.viewmodel.AuthState
import com.example.shoppingapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(
    onSearchBarFocused: () -> Unit,
    onNavigateToAuth: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.authState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), // 加入捲動效果
    ) {
        SearchContent(
            query = "",
            onQueryChanged = {},
            onSearchClicked = {},
            onImageCaptured = {},
            showBarcodeScanner = true,
            onSearchBarFocused = onSearchBarFocused,
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = authState) {
            is AuthState.Success -> {
                WelcomeSection(user = state.user)
            }
            else -> {
                GuestSection(onNavigateToAuth = onNavigateToAuth)
            }
        }

        AsyncImage(
            model = R.drawable.shopping_background,
            contentDescription = "Long Image",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )
    }
}

@Composable
fun WelcomeSection(user: FirebaseUser?) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Text(
            text = "Hi, ${user?.email ?: "User"}",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${user?.email ?: ""} pts")
    }
}

@Composable
fun GuestSection(onNavigateToAuth: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Track your rewards, check order status...",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { onNavigateToAuth() }) {
                Text("Create Account")
            }
            OutlinedButton(onClick = { onNavigateToAuth() }) {
                Text("Sign In")
            }
        }
    }
}
