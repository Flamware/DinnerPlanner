package com.example.dinnerplanner.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun getRecipesOrderedByRecipeName(): Flow<List<Recipe>>

    // Replace 'ingredients' and 'instructions' with actual fields if they exist in your Recipe class



    @Query("INSERT INTO recipes (title, instructions) VALUES (:title, :instructions)")
    suspend fun addRecipe(title: String, instructions: String)

}