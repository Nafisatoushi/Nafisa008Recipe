package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.R

@Composable
fun RecipeDetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text("Recipe Detail", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.pasta),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Ingredients & Steps:\n\n" +
                    "Step 1: Chop the vegetables\n" +
                    "Step 2: Boil water\n" +
                    "Step 3: Add pasta\n" +
                    "Step 4: Cook for 10 minutes\n" +
                    "Step 5: Drain and serve",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
