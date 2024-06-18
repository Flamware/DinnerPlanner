package com.example.dinnerplanner.data.local.database.dao

import androidx.room.*
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertIngredient(ingredient: Ingredient)
    @Update
    suspend fun update(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<Ingredient>>

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsForRecipe(recipeId: Long)

    //getAllIngredients
    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun ingredientsByRecipeId(recipeId: Long?): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun getIngredientsByRecipeId(recipeId: Long): Flow<List<Ingredient>>

}