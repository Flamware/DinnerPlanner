package com.example.dinnerplanner

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY recipeName ASC")
    fun getRecipesOrderedByRecipeName(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe ORDER BY ingredients ASC")
    fun getRecipesOrderedByIngredients(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe ORDER BY instructions ASC")
    fun getRecipesOrderedByInstructions(): Flow<List<Recipe>>
}