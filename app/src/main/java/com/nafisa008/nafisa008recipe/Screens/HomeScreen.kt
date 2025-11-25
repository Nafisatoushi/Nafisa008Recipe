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
import androidx.compose.ui.draw.clip
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
            .background(Color(0xFFFFFBE6)) // soft yellow
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp) // 👉 NOW this fixes the spacing!
    ) {

        // ---- VIEW RECIPES ----
        HomeOptionCard(
            imageRes = R.drawable.pasta,
            label = "View Recipes",
            onClick = onViewRecipes
        )

        Spacer(Modifier.height(18.dp))

        // ---- ADD RECIPE ----
        HomeOptionCard(
            imageRes = R.drawable.home,
            label = "Add New Recipe",
            onClick = onAddRecipe
        )

        Spacer(Modifier.height(18.dp))

        // ---- HISTORY / FAVORITES ----
        HomeOptionCard(
            imageRes = R.drawable.biriyani,
            label = "Favorites / History",
            onClick = onHistory
        )
    }
}

@Composable
fun HomeOptionCard(
    imageRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
