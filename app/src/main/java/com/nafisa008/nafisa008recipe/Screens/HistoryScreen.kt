package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun HistoryScreen(
    recipesFlow: StateFlow<List<Recipe>>,
    onRecipeClick: (Int) -> Unit
) {
    val recipes by recipesFlow.collectAsState()

    val favoriteRecipes = recipes.filter { it.isFavorite }
    // Show only recipes added in the last 24 hours
    val cutoffTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000) // 24 hours
    val recentlyAdded = recipes
        .filter { it.createdTime >= cutoffTime }
        .sortedByDescending { it.createdTime }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ⭐ FAVORITES SECTION
        Text(
            text = "Favorite Recipes ⭐",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(12.dp))

        if (favoriteRecipes.isEmpty()) {
            EmptyFavoritesMessage()
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(favoriteRecipes) { recipe ->
                    FavoriteRecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id!!) }
                    )
                }
            }
        }

        Spacer(Modifier.height(26.dp))

        // 🕒 RECENTLY ADDED SECTION
        Text(
            text = "Recently Added 🕒",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(12.dp))

        if (recentlyAdded.isEmpty()) {
            Text("No recipes yet.", color = Color.Gray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(recentlyAdded) { recipe ->
                    RecentRecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id!!) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyFavoritesMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("No favorite recipes yet ⭐", color = Color.Gray)
    }
}

@Composable
fun FavoriteRecipeCard(recipe: Recipe, onClick: () -> Unit) {
    RecipeCardBase(recipe = recipe, onClick = onClick, showStar = true)
}

@Composable
fun RecentRecipeCard(recipe: Recipe, onClick: () -> Unit) {
    RecipeCardBase(recipe = recipe, onClick = onClick, showStar = false)
}

@Composable
fun RecipeCardBase(
    recipe: Recipe,
    onClick: () -> Unit,
    showStar: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.background(Color.White)) {

            // IMAGE
            if (recipe.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(recipe.imageUri),
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (showStar) recipe.title + " ⭐" else recipe.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = recipe.ingredients.take(60) + "...",
                    color = Color.DarkGray
                )
            }
        }
    }
}
