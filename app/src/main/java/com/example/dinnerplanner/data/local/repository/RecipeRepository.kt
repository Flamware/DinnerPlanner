package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getRecipesOrderedByRecipeName(): Flow<List<Recipe>> = recipeDao.getRecipesOrderedByRecipeName()
    suspend fun insert(recipe: Recipe): Long {
        return recipeDao.insert(recipe)
    }

    suspend fun upsertRecipe(recipe: Recipe) {
        println("upsertRecipe: $recipe")
        recipeDao.upsertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}
