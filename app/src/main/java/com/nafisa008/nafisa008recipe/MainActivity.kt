package com.nafisa008.nafisa008recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nafisa008.nafisa008recipe.data.AppDatabase
import com.nafisa008.nafisa008recipe.data.RecipeRepository
import com.nafisa008.nafisa008recipe.screens.AddRecipeScreen
import com.nafisa008.nafisa008recipe.screens.EditRecipeScreen
import com.nafisa008.nafisa008recipe.screens.HistoryScreen
import com.nafisa008.nafisa008recipe.screens.HomeScreen
import com.nafisa008.nafisa008recipe.screens.RecipeDetailScreen
import com.nafisa008.nafisa008recipe.screens.RecipeListScreen
import com.nafisa008.nafisa008recipe.ui.theme.Nafisa008RecipeTheme
import com.nafisa008.nafisa008recipe.viewmodel.RecipeViewModel

class RecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Nafisa008RecipeTheme {

                val navController = rememberNavController()

                // DB + DAO + Repository + ViewModel
                val db = AppDatabase.getDatabase(applicationContext)
                val dao = db.recipeDao()
                val repository = RecipeRepository(dao)
                val viewModel: RecipeViewModel =
                    viewModel(factory = RecipeViewModelFactory(repository))

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("RecipeTalk") }
                        )
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        // HOME
                        composable("home") {
                            HomeScreen(
                                onViewRecipes = { navController.navigate("recipe_list") },
                                onAddRecipe = { navController.navigate("add_recipe") },
                                onHistory = { navController.navigate("history") }
                            )
                        }

                        // LIST
                        composable("recipe_list") {
                            RecipeListScreen(
                                recipesFlow = viewModel.recipes,
                                onRecipeClick = { id ->
                                    navController.navigate("recipe_detail/$id")
                                },
                                onAddRecipeClick = { navController.navigate("add_recipe") },
                                onFavoriteToggle = { recipe ->
                                    viewModel.toggleFavorite(recipe)
                                }
                            )
                        }

                        // DETAIL
                        composable("recipe_detail/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                            RecipeDetailScreen(
                                recipeId = id,
                                recipesFlow = viewModel.recipes,
                                onEditClick = { editId ->
                                    navController.navigate("edit_recipe/$editId")
                                },
                                onDeleteClick = { recipe ->
                                    viewModel.deleteRecipe(recipe)
                                    navController.popBackStack()
                                },
                                onDuplicateClick = { recipe ->
                                    navController.navigate("duplicate_recipe/${recipe.id}")
                                }
                            )
                        }

                        // DUPLICATE
                        composable("duplicate_recipe/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                            val recipeList by viewModel.recipes.collectAsState()
                            val recipe = recipeList.firstOrNull { it.id == id }

                            AddRecipeScreen(
                                viewModel = viewModel,
                                prefillRecipe = recipe
                            )
                        }

                        // HISTORY / FAVORITES
                        composable("history") {
                            HistoryScreen(
                                recipesFlow = viewModel.recipes,
                                onRecipeClick = { id ->
                                    navController.navigate("recipe_detail/$id")
                                }
                            )
                        }

                        // ADD NEW
                        composable("add_recipe") {
                            AddRecipeScreen(
                                viewModel = viewModel,
                                prefillRecipe = null
                            )
                        }

                        // EDIT
                        composable("edit_recipe/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                            val recipeList by viewModel.recipes.collectAsState()
                            val recipe = recipeList.firstOrNull { it.id == id }

                            EditRecipeScreen(
                                recipe = recipe,
                                onSaveEditedRecipe = { updated ->
                                    viewModel.updateRecipe(updated)
                                    navController.popBackStack()
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}
