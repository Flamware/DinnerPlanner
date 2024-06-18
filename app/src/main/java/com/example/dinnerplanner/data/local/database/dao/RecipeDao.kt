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

    @Query("INSERT INTO recipes (title, instructions) VALUES (:title, :instructions)")
    suspend fun addRecipe(title: String, instructions: String)

    @Query("SELECT * FROM recipes WHERE userId = :userId")
    fun getAllRecipesByUserId(userId: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :search || '%'")
    fun searchRecipes(search: String): Flow<List<Recipe>>
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun recipeById(recipeId: Long?): Recipe?
    @Query("SELECT * FROM recipes WHERE id IN (:recipeIds)")
    fun getRecipesByIds(recipeIds: List<Long>): List<Recipe>
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Long): Recipe?
}
