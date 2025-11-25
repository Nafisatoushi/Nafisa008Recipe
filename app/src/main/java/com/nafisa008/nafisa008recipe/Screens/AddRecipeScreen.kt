package com.nafisa008.nafisa008recipe.screens

import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
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
    prefillRecipe: Recipe? = null
) {
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.marimba) }
    var isSoundOn by remember { mutableStateOf(true) }

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

    // Image Picker
    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // TITLE HEADER
        Text(
            text = if (prefillRecipe == null) "Add New Recipe" else "Duplicate Recipe",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF5D4037),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .background(
                    color = Color(0xFFFFE9A3),     // soft pale yellow highlight
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )


        // TITLE FIELD (Improved)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Recipe Title") },
            placeholder = { Text("e.g., Creamy Chicken Pasta") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )

        // INGREDIENTS FIELD (Improved)

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            placeholder = { Text("List ingredients separated by commas...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(bottom = 16.dp),
            maxLines = 10,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )

        // STEPS FIELD (Improved)

        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Steps") },
            placeholder = { Text("Describe the steps clearly...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 18.dp),
            maxLines = 12,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD54F),
                focusedLabelColor = Color(0xFF8D6E63)
            )
        )

        // Image pick
        Button(
            onClick = { picker.launch("image/*") },
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text("Choose Image")
        }

        // Preview
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 16.dp)
            )
        }

        // TAG DROPDOWN
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                value = "Select Tags (${selectedTags.size})",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
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

        if (selectedTags.isNotEmpty()) {
            Text(
                text = selectedTags.joinToString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6D4C41),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // SAVE BUTTON
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (title.isNotBlank()) {
                    if (isSoundOn) mediaPlayer.start()

                    val recipe = Recipe(
                        id = 0,
                        title = title,
                        ingredients = ingredients,
                        steps = steps,
                        imageUri = imageUri?.toString(),
                        tags = selectedTags.toList(),
                        isFavorite = false
                    )
                    viewModel.addRecipe(recipe)

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

        TextButton(
            onClick = {
                isSoundOn = !isSoundOn
                if (!isSoundOn) mediaPlayer.pause()
            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(if (isSoundOn) "Turn Sound OFF" else "Turn Sound ON")
        }
    }

    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }
    }
}
