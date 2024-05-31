package com.example.dinnerplanner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
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
}

