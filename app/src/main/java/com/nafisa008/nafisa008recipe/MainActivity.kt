package com.nafisa008.nafisa008recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nafisa008.nafisa008recipe.data.Recipe
import com.nafisa008.nafisa008recipe.screens.AddRecipeScreen
import com.nafisa008.nafisa008recipe.screens.EditRecipeScreen
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

                // This is the in-memory list – it will reset when app restarts
                val recipes = remember { mutableStateListOf<Recipe>() }

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

                        // Home
                        composable("home") {
                            HomeScreen(
                                onViewRecipes = { navController.navigate("recipe_list") },
                                onAddRecipe = { navController.navigate("add_recipe") },
                                onHistory = { navController.navigate("history") }
                            )
                        }

                        // Recipe list
                        composable("recipe_list") {
                            RecipeListScreen(
                                recipes = recipes,
                                onRecipeClick = { index ->
                                    navController.navigate("recipe_detail/$index")
                                },
                                onAddRecipeClick = {
                                    navController.navigate("add_recipe")
                                }
                            )
                        }


                        // Recipe detail
                        composable("recipe_detail/{index}") { backStackEntry ->
                            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: -1
                            val recipe = recipes.getOrNull(index)

                            RecipeDetailScreen(
                                recipe = recipe,
                                recipeIndex = index,
                                onEditClick = { editIndex ->
                                    navController.navigate("edit_recipe/$editIndex")
                                },
                                onDeleteClick = { deleteIndex ->
                                    recipes.removeAt(deleteIndex)
                                    navController.popBackStack()
                                },
                                onDuplicateClick = { dupIndex ->
                                    navController.navigate("duplicate_recipe/$dupIndex")
                                }
                            )

                        }


                        // Duplicate recipe → opens AddRecipeScreen with pre-filled data
                        composable("duplicate_recipe/{index}") { backStackEntry ->
                            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: -1
                            val recipe = recipes.getOrNull(index)

                            AddRecipeScreen(
                                prefillRecipe = recipe,   // IMPORTANT
                                onSaveRecipe = { newRecipe ->
                                    recipes.add(0, newRecipe)
                                }
                            )
                        }



                        // History
                        composable("history") {
                            HistoryScreen()
                        }

                        // ➕ Add recipe
                        composable("add_recipe") {
                            AddRecipeScreen(
                                onSaveRecipe = { newRecipe ->
                                    recipes.add(0, newRecipe)
                                    // Do NOT navigate anywhere
                                }


                            )
                        }
                        // Edit recipe screen
                        composable("edit_recipe/{index}") { backStackEntry ->
                            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: -1
                            val recipe = recipes.getOrNull(index)

                            EditRecipeScreen(
                                recipe = recipe,
                                onSaveEditedRecipe = { updatedRecipe ->
                                    recipes[index] = updatedRecipe   // update list
                                    navController.popBackStack()    // go back to detail
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}
