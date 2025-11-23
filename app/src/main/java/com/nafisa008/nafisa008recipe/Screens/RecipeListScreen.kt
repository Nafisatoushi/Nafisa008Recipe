package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.data.Recipe
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    recipesFlow: StateFlow<List<Recipe>>,
    onRecipeClick: (Int) -> Unit,
    onAddRecipeClick: () -> Unit,
    onFavoriteToggle: (Recipe) -> Unit
) {
    val recipes by recipesFlow.collectAsState()

    // FILTER STATE
    var selectedFilter by remember { mutableStateOf("All") }

    // SEARCH STATE
    var searchText by remember { mutableStateOf("") }

    val filterOptions = listOf(
        "All", "Breakfast", "Lunch", "Dinner",
        "Vegetarian", "Non-Vegetarian", "Halal", "Quick"
    )

    // FILTER + SEARCH
    val filteredRecipes = recipes.filter { recipe ->

        val matchesTag =
            selectedFilter == "All" || selectedFilter in recipe.tags

        val matchesSearch =
            recipe.title.contains(searchText, ignoreCase = true) ||
                    recipe.ingredients.contains(searchText, ignoreCase = true)

        matchesTag && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "All Recipes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // SEARCH BAR
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search recipes...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // FILTER BAR
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 12.dp)
        ) {
            filterOptions.forEach { filter ->
                FilterChip(
                    label = filter,
                    isSelected = (filter == selectedFilter),
                    onClick = { selectedFilter = filter }
                )
                Spacer(Modifier.width(6.dp))
            }
        }

        // EMPTY STATE
        if (filteredRecipes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No recipes found.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Tap here to add a new recipe",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onAddRecipeClick() },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            return
        }

        // RECIPE LIST
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filteredRecipes) { recipe ->
                SimpleRecipeRow(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id ?: -1) },
                    onFavoriteToggle = { onFavoriteToggle(recipe) }
                )
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else Color(0xFFE3E3E3)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SimpleRecipeRow(
    recipe: Recipe,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFF8F8F8))
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // TITLE
            Text(
                text = "🍽  " + recipe.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            // STAR
            Text(
                text = if (recipe.isFavorite) "⭐" else "☆",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable { onFavoriteToggle() }
            )
        }
    }
}
