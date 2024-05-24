package com.example.dinnerplanner.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.dinnerplanner.data.local.entity.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Insert a new recipe and return the recipeId

    @Insert
    suspend fun insert(recipe: Recipe): Long
    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("INSERT INTO ingredients (recipeId, name, quantity) VALUES (:recipeId, :ingredientName, :ingredientQuantity)")
    suspend fun addIngredient(recipeId: Int, ingredientName: String, ingredientQuantity: Double)

    @Query("SELECT * FROM recipes WHERE userId = :userId")
    suspend fun getAllRecipesForUser(userId: String): LiveData<List<Recipe>>

}