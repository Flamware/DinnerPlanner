package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.LikeDao
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.entity.Like
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.Flow


class RecipeRepository(private val recipeDao: RecipeDao, private val likeDao: LikeDao) {
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

    fun getAllRecipesByUserId(userId: Int): Flow<List<Recipe>> {
        return recipeDao.getAllRecipesByUserId(userId)
    }

    fun searchRecipes(search: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipes(search)
    }

    suspend fun recipeById(recipeId: Long?): Recipe? {
        println("recipeById: $recipeId")
        return recipeDao.recipeById(recipeId)
    }
    fun getLikes(): Flow<List<Like>> = likeDao.getLikes()

    suspend fun likeRecipe(recipeId: Long, userId: Int) {
        likeDao.likeRecipe(recipeId, userId)
    }

    suspend fun unlikeRecipe(id: Long, id1: Int) {
        likeDao.unlikeRecipe(id, id1)
    }

    suspend fun isLiked(id: Long, i: Int): Any {
        return likeDao.isLiked(id, i)
    }

    fun getRecipesByIds(recipeIds: List<Long>): List<Recipe> {
        return recipeIds.flatMap { recipeId ->
            listOfNotNull(recipeDao.getRecipeById(recipeId))
        }
    }

    fun getRecipeById(recipeId: Long): Recipe? {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getRecipeByIdFlow(recipeId: Long): Flow<Recipe?> {
        return recipeDao.getRecipeByIdFlow(recipeId)
    }


}
