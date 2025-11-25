package com.nafisa008.nafisa008recipe

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
import com.nafisa008.nafisa008recipe.worker.ReminderWorker
import java.util.concurrent.TimeUnit

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

    // SAFE Notification channel (no API errors)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "recipe_channel",
                "Recipe Reminders",
                android.app.NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(android.app.NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create notification channel BEFORE WorkManager
        createNotificationChannel()

        // Schedule daily reminder worker
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(
            1, TimeUnit.DAYS
        ).build()

        val testRequest = androidx.work.OneTimeWorkRequestBuilder<ReminderWorker>()
            .build()

        WorkManager.getInstance(this).enqueue(testRequest)


        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_recipe_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )

        setContent {
            Nafisa008RecipeTheme {

                val navController = rememberNavController()

                // DB + DAO + Repository + ViewModel
                val db = AppDatabase.getDatabase(applicationContext)
                val dao = db.recipeDao()
                val repository = RecipeRepository(dao)
                val viewModel: RecipeViewModel =
                    viewModel(factory = RecipeViewModelFactory(repository))

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xFFFFE082),
                                            shape = RoundedCornerShape(18.dp)
                                        )
                                        .padding(horizontal = 20.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "RecipeTalk",
                                        color = Color(0xFF5D4037),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFFFFFBE6)
                            )
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

                        composable("duplicate_recipe/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                            val recipeList by viewModel.recipes.collectAsState()
                            val recipe = recipeList.firstOrNull { it.id == id }

                            AddRecipeScreen(
                                viewModel = viewModel,
                                prefillRecipe = recipe
                            )
                        }

                        composable("history") {
                            HistoryScreen(
                                recipesFlow = viewModel.recipes,
                                onRecipeClick = { id ->
                                    navController.navigate("recipe_detail/$id")
                                }
                            )
                        }

                        composable("add_recipe") {
                            AddRecipeScreen(
                                viewModel = viewModel,
                                prefillRecipe = null
                            )
                        }

                        composable("edit_recipe/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                            val recipeList by viewModel.recipes.collectAsState()
                            val recipe = recipeList.firstOrNull { it.id == id }

                            EditRecipeScreen(
                                recipe = recipe,
                                viewModel = viewModel,
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
