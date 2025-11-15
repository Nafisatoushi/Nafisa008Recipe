package com.nafisa008.nafisa008recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nafisa008.nafisa008recipe.screens.AddRecipeScreen
import com.nafisa008.nafisa008recipe.screens.HistoryScreen
import com.nafisa008.nafisa008recipe.screens.HomeScreen
import com.nafisa008.nafisa008recipe.screens.RecipeDetailScreen
import com.nafisa008.nafisa008recipe.screens.RecipeListScreen
import com.nafisa008.nafisa008recipe.ui.theme.Nafisa008RecipeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nafisa008RecipeTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Nafisa’s Recipe App") }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onViewRecipes = { navController.navigate("recipe_list") },
                                onAddRecipe = { navController.navigate("add_recipe") },
                                onHistory = { navController.navigate("history") }
                            )
                        }
                        composable("recipe_list") {
                            RecipeListScreen(
                                onRecipeClick = {
                                    navController.navigate("recipe_detail")
                                }
                            )
                        }
                        composable("recipe_detail") {
                            RecipeDetailScreen()
                        }
                        composable("history") {
                            HistoryScreen()
                        }
                        composable("add_recipe") {
                            AddRecipeScreen()
                        }
                    }
                }
            }
        }
    }
}
