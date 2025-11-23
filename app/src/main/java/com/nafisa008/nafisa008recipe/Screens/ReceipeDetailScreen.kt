package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nafisa008.nafisa008recipe.data.Recipe
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    recipesFlow: StateFlow<List<Recipe>>,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Recipe) -> Unit,
    onDuplicateClick: (Recipe) -> Unit
) {
    val recipes by recipesFlow.collectAsState()

    // Find recipe by ID
    val recipe = recipes.firstOrNull { it.id == recipeId }

    if (recipe == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Recipe not found.", style = MaterialTheme.typography.titleLarge)
        }
        return
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        // TITLE
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))

        // TAGS
        if (recipe.tags.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                recipe.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .padding(end = 6.dp, bottom = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE0E7FF))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1D2A5B)
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }

        // IMAGE
        if (recipe.imageUri != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(recipe.imageUri),
                    contentDescription = recipe.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        // INGREDIENTS
        Text("Ingredients", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))

        val ingredientLines = recipe.ingredients.split('\n')
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        if (ingredientLines.isEmpty()) {
            Text("No ingredients listed.", color = Color.Gray)
        } else {
            ingredientLines.forEach { line ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Text("• ")
                    Text(line)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // STEPS
        Text("Steps", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))

        val stepLines = recipe.steps.split('\n')
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        if (stepLines.isEmpty()) {
            Text("No steps listed.", color = Color.Gray)
        } else {
            stepLines.forEachIndexed { index, line ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Text("${index + 1}. ")
                    Text(line)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = { onEditClick(recipe.id!!) }) {
                Text("Edit")
            }

            Button(onClick = { onDeleteClick(recipe) }) {
                Text("Delete")
            }

            Button(onClick = { onDuplicateClick(recipe) }) {
                Text("Duplicate")
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}
