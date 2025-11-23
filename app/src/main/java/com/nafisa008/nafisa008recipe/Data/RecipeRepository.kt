package com.nafisa008.nafisa008recipe.data

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val dao: RecipeDao) {

    // Now returns Flow<List<Recipe>>
    val recipesFlow: Flow<List<Recipe>> =
        dao.getAllRecipesFlow()

    suspend fun insert(recipe: Recipe) {
        dao.insertRecipe(recipe)
    }

    suspend fun update(recipe: Recipe) {
        dao.updateRecipe(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }
}
