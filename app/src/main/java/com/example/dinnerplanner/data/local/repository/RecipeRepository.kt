package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getRecipesOrderedByRecipeName(): Flow<List<Recipe>> = recipeDao.getRecipesOrderedByRecipeName()

    fun getRecipesOrderedByIngredients(): Flow<List<Recipe>> = recipeDao.getRecipesOrderedByIngredients()

    fun getRecipesOrderedByInstructions(): Flow<List<Recipe>> = recipeDao.getRecipesOrderedByInstructions()

    suspend fun upsertRecipe(recipe: Recipe) {
        recipeDao.upsertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}
