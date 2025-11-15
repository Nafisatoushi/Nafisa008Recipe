package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // For now, some sample recipes
    val recipes = listOf("Pasta", "Chicken Curry", "Biriyani")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Recipes") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(recipes) { name ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecipeClick() }   // for now, all go to same detail
                        .padding(16.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Divider()
            }
        }
    }
}
