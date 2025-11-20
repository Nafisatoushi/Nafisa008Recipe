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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    prefillRecipe: Recipe? = null,     // ⭐ NEW: Prefill support for Duplicate + Edit
    onSaveRecipe: (Recipe) -> Unit
) {
    val context = LocalContext.current

    // Sound
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.marimba) }
    var isSoundOn by remember { mutableStateOf(true) }

    // MAIN FIELDS (with prefill support)
    var title by remember { mutableStateOf(prefillRecipe?.title ?: "") }
    var ingredients by remember { mutableStateOf(prefillRecipe?.ingredients ?: "") }
    var steps by remember { mutableStateOf(prefillRecipe?.steps ?: "") }
    var imageUri by remember {
        mutableStateOf(
            prefillRecipe?.imageUri?.let { Uri.parse(it) }
        )
    }

    // TAG LIST
    val availableTags = listOf(
        // Meal type
        "Breakfast", "Lunch", "Dinner", "Snack", "Dessert",
        // Diet
        "Vegetarian", "Non-Vegetarian", "Vegan", "Halal", "Gluten-free",
        // Style
        "Healthy", "Quick", "Spicy", "Sweet", "Budget"
    )

    // SELECTED TAGS (with prefill support)
    val selectedTags = remember {
        mutableStateListOf<String>().apply {
            prefillRecipe?.tags?.forEach { add(it) }
        }
    }

    var dropdownExpanded by remember { mutableStateOf(false) }

    // Image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = if (prefillRecipe == null) "Add New Recipe" else "Duplicate Recipe",
            style = MaterialTheme.typography.titleLarge
        )
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
        Spacer(Modifier.height(16.dp))

        // IMAGE PICKER
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Choose Image")
        }
        Spacer(Modifier.height(12.dp))

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
                value = "Select Tags",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text("Tags") },
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

        Spacer(Modifier.height(12.dp))

        // SHOW SELECTED TAGS
        if (selectedTags.isNotEmpty()) {
            Text(
                text = "Selected: " + selectedTags.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(20.dp))

        // SAVE BUTTON
        Button(
            onClick = {
                if (title.isNotBlank()) {

                    // play save sound
                    if (isSoundOn) mediaPlayer.start()

                    val newRecipe = Recipe(
                        title = title,
                        ingredients = ingredients,
                        steps = steps,
                        imageUri = imageUri?.toString(),
                        tags = selectedTags.toList()
                    )

                    onSaveRecipe(newRecipe)

                    // CLEAR for new entry
                    title = ""
                    ingredients = ""
                    steps = ""
                    imageUri = null
                    selectedTags.clear()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Recipe")
        }

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

    // Clean up sound
    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }
    }
}
