package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.data.Recipe

@Composable
fun EditRecipeScreen(
    recipe: Recipe?,
    onSaveEditedRecipe: (Recipe) -> Unit
) {
    if (recipe == null) {
        Text("Error loading recipe.")
        return
    }

    // STATE
    var title by remember { mutableStateOf(recipe.title) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }
    var steps by remember { mutableStateOf(recipe.steps) }
    var imageUri = recipe.imageUri         // keep old image
    var tags by remember { mutableStateOf(recipe.tags) } // later add UI for tags

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Edit Recipe", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        // TITLE
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Recipe Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // INGREDIENTS
        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(Modifier.height(12.dp))

        // STEPS
        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Steps") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(Modifier.height(20.dp))

        // SAVE BUTTON
        Button(
            onClick = {
                onSaveEditedRecipe(
                    recipe.copy(
                        title = title,
                        ingredients = ingredients,
                        steps = steps,
                        imageUri = imageUri,
                        tags = tags
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
