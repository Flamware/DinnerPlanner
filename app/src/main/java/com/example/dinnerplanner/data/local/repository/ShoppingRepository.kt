package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.ShoppingListDao
import com.example.dinnerplanner.data.local.database.entity.ShoppingListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class ShoppingRepository(private val shoppingListDao: ShoppingListDao) {

    suspend fun insert(item: ShoppingListItem) {
        shoppingListDao.insert(item)
    }

    suspend fun update(item: ShoppingListItem) {
        shoppingListDao.update(item)
    }

    suspend fun delete(item: Int) {
        shoppingListDao.delete(item)
    }

    suspend fun getShoppingListByRecipeId(recipeId: Long): List<ShoppingListItem> {
        return shoppingListDao.getShoppingListByRecipeId(recipeId)
    }

    suspend fun getShoppingListByIngredientId(ingredientId: Long): List<ShoppingListItem> {
        return shoppingListDao.getShoppingListByIngredientId(ingredientId)
    }

    suspend fun getAllShoppingListItems(): List<ShoppingListItem> {
        return shoppingListDao.getAllShoppingListItems()
    }

    fun getShoppingListByRecipeIdAndIngredientId(recipeId: Long, ingredientId: Long, userId: Int): Flow<ShoppingListItem?> {
        return shoppingListDao.getShoppingListByRecipeIdAndIngredientId(recipeId, ingredientId, userId)
    }

    fun deleteAllShoppingListItems() {
        shoppingListDao.deleteAllShoppingListItems()
    }

     fun getShoppingListByUserId(userId: Int): Flow<List<ShoppingListItem>> {
        return shoppingListDao.getShoppingListByUserId(userId)
    }

    suspend fun getShoppingItem(recipeId: Long, ingredientId: Long, userId: Int): ShoppingListItem? {
        return shoppingListDao.getShoppingItem(recipeId, ingredientId, userId).firstOrNull()
    }

}