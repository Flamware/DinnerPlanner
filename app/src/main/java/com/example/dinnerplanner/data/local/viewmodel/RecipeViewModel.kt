package com.example.dinnerplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val recipes: StateFlow<List<Recipe>> = repository.getRecipesOrderedByRecipeName()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        println("RecipeViewModel init")
        printRecipes()
    }

    private fun printRecipes() = viewModelScope.launch {
        recipes.collect { recipeList ->
            recipeList.forEach { recipe ->
                println(recipe)
                println("Title: ${recipe.title}")
            }
        }
    }

    fun upsertRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.upsertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

}