package com.example.dinnerplanner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Recipe>>()
    val searchResults: LiveData<List<Recipe>> = _searchResults

    private val _SelectedRecipe = MutableLiveData<Recipe?>()
    val SelectedRecipe: MutableLiveData<Recipe?> = _SelectedRecipe

    val recipes: StateFlow<List<Recipe>> = repository.getRecipesOrderedByRecipeName()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private fun printRecipes() = viewModelScope.launch {
        recipes.collect { recipeList ->
            recipeList.forEach { recipe ->
                println(recipe)
                println("Title: ${recipe.title}")
            }
        }
    }

    suspend fun insert(recipe: Recipe): Long {
        return repository.insert(recipe)
    }
    suspend fun likeRecipe(recipeId: Long, userId: Int) {
        println("likeRecipe: $recipeId, $userId")
        repository.likeRecipe(recipeId, userId)
    }

    fun upsertRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.upsertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

    fun getAllRecipesByUserId(userId: Int): StateFlow<List<Recipe>> {
        return repository.getAllRecipesByUserId(userId)
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    }

    fun searchRecipes(newText: String) {
        repository.searchRecipes(newText).asLiveData().observeForever {
            _searchResults.value = it
        }
    }

    suspend fun recipeById(id: Long?): Recipe? {
        var recipe: Recipe? = null
        viewModelScope.launch {
            recipe = repository.recipeById(id)
            _SelectedRecipe.value = recipe
        }
        return recipe
    }

    fun unlikeRecipe(id: Long, id1: Int) {
        viewModelScope.launch {
            repository.unlikeRecipe(id, id1)
        }
    }

    suspend fun isRecipeLiked(id: Long, i: Int): Any {
        return repository.isLiked(id, i)
    }
}



