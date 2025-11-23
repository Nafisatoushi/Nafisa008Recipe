package com.nafisa008.nafisa008recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.R

@Composable
fun HomeScreen(
    onViewRecipes: () -> Unit,
    onAddRecipe: () -> Unit,
    onHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("RecipeTalk", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        // View Recipes Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onViewRecipes() }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pasta),
                contentDescription = "View Recipes",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "View Recipes",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        // Add New Recipe Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onAddRecipe() }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Add New Recipe",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "Add New Recipe",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        // Favorites / History Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onHistory() }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.biriyani),
                contentDescription = "Favorites / History",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "Favorites / History",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
