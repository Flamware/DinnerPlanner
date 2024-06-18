package com.example.dinnerplanner.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IngredientViewModel(private val repository: IngredientRepository) : ViewModel() {
    val ingredients: Flow<List<Ingredient>> = repository.getAllIngredients()

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

    fun ingredientsByRecipeId(recipeId: Long?): Flow<List<Ingredient>> {
        return repository.ingredientsByRecipeId(recipeId)
    }



    fun getIngredientsByRecipeId(recipeId: Long): Flow<List<Ingredient>> {
        return repository.getIngredientsByRecipeId(recipeId)
    }

}
