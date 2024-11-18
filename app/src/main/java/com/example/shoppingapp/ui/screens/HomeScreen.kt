package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.components.SearchBar
import com.example.shoppingapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column {
        SearchBar(
            query = query,
            onQueryChanged = { viewModel.onQueryChanged(it) },
            onSearchClicked = { viewModel.onSearchClicked() },
            onBarcodeScanClicked = { viewModel.onBarcodeScanClicked() }
        )

        LazyColumn {
            items(searchResults) { product ->
                Text(text = product.name)
            }
        }
    }
}