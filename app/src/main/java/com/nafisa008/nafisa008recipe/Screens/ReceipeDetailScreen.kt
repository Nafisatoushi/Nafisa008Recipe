package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nafisa008.nafisa008recipe.data.Recipe

@Composable
fun RecipeDetailScreen(
    recipe: Recipe?,
    recipeIndex: Int,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onDuplicateClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        if (recipe == null) {
            Text("Recipe not found.", style = MaterialTheme.typography.titleLarge)
            return
        }

        Text(recipe.title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        // Show big image
        if (recipe.imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(recipe.imageUri),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
            Spacer(Modifier.height(16.dp))
        }

        Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
        Text(recipe.ingredients)
        Spacer(Modifier.height(16.dp))

        Text("Steps:", style = MaterialTheme.typography.titleMedium)
        Text(recipe.steps)
        Spacer(Modifier.height(24.dp))

        // BUTTONS ROW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onEditClick(recipeIndex) }) {
                Text("Edit")
            }

            Button(onClick = { onDeleteClick(recipeIndex) }) {
                Text("Delete")
            }

            Button(onClick = { onDuplicateClick(recipeIndex) }) {
                Text("Duplicate")
            }
        }
    }
}
