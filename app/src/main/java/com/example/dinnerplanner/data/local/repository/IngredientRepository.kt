package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.IngredientDao
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IngredientRepository(private val ingredientDao: IngredientDao) {

    init {
        println("IngredientRepository init")
        CoroutineScope(Dispatchers.IO).launch {
            loadAllIngredients()
        }
    }

    private suspend fun loadAllIngredients() {
        val allIngredients = ingredientDao.getAllIngredients()
        allIngredients.collect { ingredients ->
            ingredients.forEach { ingredient ->
                println(ingredient)
            }
        }
    }
    fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAllIngredients()
    }

    suspend fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

    suspend fun upsertIngredient(ingredient: Ingredient) {
        ingredientDao.upsertIngredient(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        ingredientDao.update(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        ingredientDao.delete(ingredient)
    }

    fun getIngredientsForRecipe(recipeId: Long): Flow<List<Ingredient>> {
        return ingredientDao.getIngredientsForRecipe(recipeId)
    }

    fun ingredientsByRecipeId(recipeId: Long?): Flow<List<Ingredient>> {
        return ingredientDao.ingredientsByRecipeId(recipeId)
    }
}