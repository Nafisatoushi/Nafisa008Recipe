package com.nafisa008.nafisa008recipe.screens

import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import coil.compose.rememberAsyncImagePainter
import com.nafisa008.nafisa008recipe.R
import com.nafisa008.nafisa008recipe.data.Recipe

@Composable
fun AddRecipeScreen(
    onSaveRecipe: (Recipe) -> Unit
) {
    val context = LocalContext.current

    // sound player
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.marimba)
    }

    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

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

        Spacer(Modifier.height(16.dp))

        // Choose Image button
        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")
            }
        ) {
            Text("Choose Image")
        }

        Spacer(Modifier.height(12.dp))

        // Preview selected image
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    val recipe = Recipe(
                        title = title,
                        ingredients = ingredients,
                        steps = steps,
                        imageUri = imageUri?.toString()  // save Uri as String
                    )

                    // play sound
                    mediaPlayer.start()

                    // send back to MainActivity
                    onSaveRecipe(recipe)
                }
            }
        ) {
            Text("Save Recipe")
        }
    }

    // clean up player when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
