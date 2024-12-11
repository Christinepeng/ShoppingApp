package com.example.shoppingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.viewmodel.SuggestionViewModel

@Composable
fun SuggestionListScreen(
    query: String,
    onSuggestionClicked: (String) -> Unit,
    onBack: () -> Unit,
) {
    val viewModel: SuggestionViewModel = hiltViewModel()
    val suggestions by viewModel.suggestions.collectAsState()

    LaunchedEffect(query) {
        viewModel.fetchSuggestions(query)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(
            text = "Suggestions for \"$query\"",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (suggestions.isEmpty()) {
            Text(text = "No suggestions available.")
        } else {
            LazyColumn {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { onSuggestionClicked(suggestion) }
                                .padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}
