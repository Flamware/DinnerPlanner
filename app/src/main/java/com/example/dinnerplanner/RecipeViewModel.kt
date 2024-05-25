package com.example.dinnerplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModel(
    private val dao: RecipeDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.RECIPE_NAME)
    private val _recipes = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.RECIPE_NAME -> dao.getRecipesOrderedByRecipeName()
                SortType.INGREDIENTS -> dao.getRecipesOrderedByIngredients()
                SortType.INSTRUCTIONS -> dao.getRecipesOrderedByInstructions()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(RecipeState())
    val state = combine(_state, _sortType, _recipes) { state, sortType, recipes ->
        state.copy(
            recipes = recipes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecipeState())

    fun onEvent(event: RecipeEvent) {
        when(event) {
            is RecipeEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    dao.deleteRecipe(event.recipe)
                }
            }
            RecipeEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingRecipe = false
                ) }
            }
            RecipeEvent.SaveRecipe -> {
                val recipeName = state.value.recipeName
                val ingredients = state.value.ingredients
                val instructions = state.value.instructions

                if(recipeName.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                    return
                }

                val recipe = Recipe(
                    recipeName = recipeName,
                    ingredients = ingredients,
                    instructions = instructions
                )
                viewModelScope.launch {
                    dao.upsertRecipe(recipe)
                }
                _state.update { it.copy(
                    isAddingRecipe = false,
                    recipeName = "",
                    ingredients = "",
                    instructions = ""
                ) }
            }
            is RecipeEvent.SetRecipeName -> {
                _state.update { it.copy(
                    recipeName = event.recipeName
                ) }
            }
            is RecipeEvent.SetIngredients -> {
                _state.update { it.copy(
                    ingredients = event.ingredients
                ) }
            }
            is RecipeEvent.SetInstructions -> {
                _state.update { it.copy(
                    instructions = event.instructions
                ) }
            }
            RecipeEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingRecipe = true
                ) }
            }
            is RecipeEvent.SortRecipes -> {
                _sortType.value = event.sortType
            }
        }
    }
}