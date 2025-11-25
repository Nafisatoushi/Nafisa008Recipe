package com.nafisa008.nafisa008recipe.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nafisa008.nafisa008recipe.data.Recipe
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    recipesFlow: StateFlow<List<Recipe>>,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Recipe) -> Unit,
    onDuplicateClick: (Recipe) -> Unit
) {
    val recipes by recipesFlow.collectAsState()
    val recipe = recipes.firstOrNull { it.id == recipeId }
    val context = LocalContext.current   // ← Needed for sharing

    if (recipe == null) {
        Text("Recipe not found")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))
            .padding(16.dp)
    ) {

        // MAIN CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column {

                // IMAGE
                if (recipe.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(recipe.imageUri),
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(modifier = Modifier.padding(20.dp)) {

                    // TITLE
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF4E342E)
                    )

                    Spacer(Modifier.height(12.dp))

                    // TAGS
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        recipe.tags.forEach { tag ->
                            Text(
                                text = tag,
                                modifier = Modifier
                                    .background(
                                        Color(0xFFE8E8F9),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                color = Color(0xFF4E4A53)
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // INGREDIENTS
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4E342E)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = recipe.ingredients, color = Color.DarkGray)

                    Spacer(Modifier.height(20.dp))

                    // STEPS
                    Text(
                        text = "Steps",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4E342E)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = recipe.steps, color = Color.DarkGray)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // BUTTON ROW
        // ROW 1 – Edit + Delete
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailButton("Edit") { onEditClick(recipe.id) }
            DetailButton("Delete") { onDeleteClick(recipe) }
        }

        Spacer(Modifier.height(16.dp))

// ROW 2 – Duplicate + Share
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailButton("Duplicate") { onDuplicateClick(recipe) }

            DetailButton("Share") {
                val shareText = """
            🍽 ${recipe.title}

            🧂 Ingredients:
            ${recipe.ingredients}

            👩‍🍳 Steps:
            ${recipe.steps}

            Shared from RecipeTalk!
        """.trimIndent()

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                context.startActivity(Intent.createChooser(intent, "Share Recipe via"))
            }
        }

    }
}

@Composable
fun DetailButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = text, color = Color(0xFF4E342E))
    }
}
