package com.example.dinnerplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeDao) : ViewModel() {
    val recipes: StateFlow<List<Recipe>> = repository.getRecipesOrderedByRecipeName()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun upsertRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.upsertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

    suspend fun addRecipe(title: String, joinToString: String, instructions: String) {
        repository.addRecipe(title, joinToString, instructions)
    }


}
