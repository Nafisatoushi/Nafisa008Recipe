package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.data.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        if (recipe == null) {
            Text("Recipe not found.", style = MaterialTheme.typography.titleLarge)
        } else {
            Text(recipe.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
            Text(recipe.ingredients.ifBlank { "No ingredients provided." })
            Spacer(Modifier.height(16.dp))
            Text("Steps:", style = MaterialTheme.typography.titleMedium)
            Text(recipe.steps.ifBlank { "No steps provided." })
        }
    }
}
