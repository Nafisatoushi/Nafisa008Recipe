package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.data.Recipe
import com.nafisa008.nafisa008recipe.viewmodel.RecipeViewModel

@Composable
fun EditRecipeScreen(
    recipe: Recipe?,
    viewModel: RecipeViewModel,
    onSaveEditedRecipe: (Recipe) -> Unit
) {
    if (recipe == null) {
        Text("Error loading recipe.")
        return
    }

    var title by remember { mutableStateOf(recipe.title) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }
    var steps by remember { mutableStateOf(recipe.steps) }
    var imageUri = recipe.imageUri
    var tags by remember { mutableStateOf(recipe.tags) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))       // SAME background as Add screen
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {


        Text(
            text = "Edit Recipe",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF5D4037),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .background(
                    color = Color(0xFFFFE9A3),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )


        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Recipe Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                unfocusedBorderColor = Color(0xFFE0C17E),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )


        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(bottom = 16.dp),
            maxLines = 10,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                unfocusedBorderColor = Color(0xFFE0C17E),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )


        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Steps") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 20.dp),
            maxLines = 12,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                unfocusedBorderColor = Color(0xFFE0C17E),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )


        Button(
            onClick = {
                val updated = recipe.copy(
                    title = title,
                    ingredients = ingredients,
                    steps = steps,
                    imageUri = imageUri,
                    tags = tags
                )
                viewModel.updateRecipe(updated)
                onSaveEditedRecipe(updated)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
