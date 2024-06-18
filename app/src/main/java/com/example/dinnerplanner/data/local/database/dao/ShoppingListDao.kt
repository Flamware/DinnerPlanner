package com.example.dinnerplanner.data.local.database.dao

import androidx.room.*
import com.example.dinnerplanner.data.local.database.entity.ShoppingListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingListItem)

    @Update
    suspend fun update(item: ShoppingListItem)

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("SELECT * FROM shopping_list WHERE userId = :userId")
    fun getShoppingListByUserId(userId: Int): Flow<List<ShoppingListItem>>
    @Query("SELECT * FROM shopping_list WHERE recipeId = :recipeId")
    suspend fun getShoppingListByRecipeId(recipeId: Long): List<ShoppingListItem>

    @Query("SELECT * FROM shopping_list WHERE ingredientId = :ingredientId")
    suspend fun getShoppingListByIngredientId(ingredientId: Long): List<ShoppingListItem>

    @Query("SELECT * FROM shopping_list")
    suspend fun getAllShoppingListItems(): List<ShoppingListItem>

    @Query("SELECT * FROM shopping_list WHERE recipeId = :recipeId AND ingredientId = :ingredientId AND userId = :userId LIMIT 1")
    fun getShoppingListByRecipeIdAndIngredientId(recipeId: Long, ingredientId: Long, userId: Int): Flow<ShoppingListItem?>
    @Query("DELETE FROM shopping_list")
    fun deleteAllShoppingListItems()

    @Query("SELECT * FROM shopping_list WHERE recipeId = :recipeId AND ingredientId = :ingredientId AND userId = :userId LIMIT 1")
    fun getShoppingItem(recipeId: Long, ingredientId: Long, userId: Int): Flow<ShoppingListItem>

}