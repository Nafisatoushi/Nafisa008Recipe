package com.nafisa008.nafisa008recipe.screens

import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nafisa008.nafisa008recipe.R
import com.nafisa008.nafisa008recipe.data.Recipe
import com.nafisa008.nafisa008recipe.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    viewModel: RecipeViewModel,
    prefillRecipe: Recipe? = null      // for Duplicate
) {
    val context = LocalContext.current

    // Sound
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.marimba) }
    var isSoundOn by remember { mutableStateOf(true) }

    // MAIN FIELDS
    var title by remember { mutableStateOf(prefillRecipe?.title ?: "") }
    var ingredients by remember { mutableStateOf(prefillRecipe?.ingredients ?: "") }
    var steps by remember { mutableStateOf(prefillRecipe?.steps ?: "") }
    var imageUri by remember {
        mutableStateOf(prefillRecipe?.imageUri?.let { Uri.parse(it) })
    }

    // TAGS
    val availableTags = listOf(
        "Breakfast", "Lunch", "Dinner", "Snack", "Dessert",
        "Vegetarian", "Non-Vegetarian", "Vegan", "Halal", "Gluten-free",
        "Healthy", "Quick", "Spicy", "Sweet", "Budget"
    )

    val selectedTags = remember {
        mutableStateListOf<String>().apply {
            prefillRecipe?.tags?.forEach { add(it) }
        }
    }

    var dropdownExpanded by remember { mutableStateOf(false) }

    // Image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = if (prefillRecipe == null) "Add New Recipe" else "Duplicate Recipe",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))

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

        Spacer(Modifier.height(16.dp))

        // IMAGE BUTTON
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Choose Image")
        }

        Spacer(Modifier.height(12.dp))

        // IMAGE PREVIEW
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }

        Spacer(Modifier.height(20.dp))


        // TAG DROPDOWN
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                value = "Select Tags (${selectedTags.size})",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                availableTags.forEach { tag ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                Checkbox(
                                    checked = tag in selectedTags,
                                    onCheckedChange = { checked ->
                                        if (checked) selectedTags.add(tag)
                                        else selectedTags.remove(tag)
                                    }
                                )
                                Text(tag)
                            }
                        },
                        onClick = {
                            if (tag in selectedTags) selectedTags.remove(tag)
                            else selectedTags.add(tag)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Selected tags
        if (selectedTags.isNotEmpty()) {
            Text(
                text = "Selected: " + selectedTags.joinToString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(20.dp))

        // SAVE BUTTON
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (title.isNotBlank()) {

                    if (isSoundOn) mediaPlayer.start()

                    val recipe = Recipe(
                        id = 0, // Room auto-generates
                        title = title,
                        ingredients = ingredients,
                        steps = steps,
                        imageUri = imageUri?.toString(),
                        tags = selectedTags.toList(),
                        isFavorite = false
                    )

                    viewModel.addRecipe(recipe)

                    // reset for next new recipe
                    title = ""
                    ingredients = ""
                    steps = ""
                    imageUri = null
                    selectedTags.clear()
                }
            }
        ) {
            Text("Save Recipe")
        }

        Spacer(Modifier.height(10.dp))

        // SOUND TOGGLE
        TextButton(
            onClick = {
                isSoundOn = !isSoundOn
                if (!isSoundOn) mediaPlayer.pause()
            }
        ) {
            Text(if (isSoundOn) "Turn Sound OFF" else "Turn Sound ON")
        }
    }

    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }
    }
}
