package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Recipes") }
            )
        }
    ) { innerPadding ->

        if (recipes.isEmpty()) {
            // ⭐ BEAUTIFUL EMPTY SCREEN
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🍽️",
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    "No recipes yet",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Tap 'Add New Recipe' to create your first one!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            // ORIGINAL LIST
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                itemsIndexed(recipes) { index, recipe ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRecipeClick(index) }
                            .padding(16.dp)
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}
