package com.example.dinnerplanner.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.repository.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(private val repository: IngredientRepository) : ViewModel() {

    fun getIngredientsForRecipe(recipeId: Long) = repository.getIngredientsForRecipe(recipeId)

    fun insert(ingredient: Ingredient) = viewModelScope.launch {
        repository.insert(ingredient)
    }

    fun update(ingredient: Ingredient) = viewModelScope.launch {
        repository.update(ingredient)
    }

    fun delete(ingredient: Ingredient) = viewModelScope.launch {
        repository.delete(ingredient)
    }

    fun upsertIngredient(copy: Ingredient) {
        viewModelScope.launch {
            repository.upsertIngredient(copy)
        }
    }
}