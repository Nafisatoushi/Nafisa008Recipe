package com.nafisa008.nafisa008recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nafisa008.nafisa008recipe.data.Recipe
import com.nafisa008.nafisa008recipe.data.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    // DIRECTLY USE Recipe from the repository
    val recipes: StateFlow<List<Recipe>> =
        repository.recipesFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // ADD
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
        }
    }

    // UPDATE
    fun updateRecipe(updated: Recipe) {
        viewModelScope.launch {
            repository.update(updated)
        }
    }

    // DELETE
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.delete(recipe)
        }
    }

    // FAVORITE TOGGLE
    fun toggleFavorite(recipe: Recipe) {
        val updated = recipe.copy(isFavorite = !recipe.isFavorite)
        updateRecipe(updated)
    }
}
