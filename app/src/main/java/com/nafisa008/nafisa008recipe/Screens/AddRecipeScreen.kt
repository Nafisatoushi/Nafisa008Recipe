package com.nafisa008.nafisa008recipe.screens

import android.media.MediaPlayer
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.R
import com.nafisa008.nafisa008recipe.data.Recipe

@Composable
fun AddRecipeScreen(
    onSaveRecipe: (Recipe) -> Unit
) {
    val context = LocalContext.current

    // Remember a MediaPlayer for the save sound
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.marimba
        )
    }

    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Add New Recipe", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Recipe Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Steps") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    val recipe = Recipe(
                        title = title,
                        ingredients = ingredients,
                        steps = steps
                    )

                    // 🔊 Play the save sound
                    mediaPlayer.start()

                    // Send recipe back to MainActivity to add to the list
                    onSaveRecipe(recipe)
                }
            }
        ) {
            Text("Save Recipe")
        }
    }

    // Clean up mediaPlayer when this screen is removed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
