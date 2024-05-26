package com.example.dinnerplanner

import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.Recipe

data class RecipeState(
        val recipes: List<Recipe> = emptyList(),
        val recipeName: String = "",
        val ingredients: List<Ingredient> = listOf(),
        val instructions: String = "",
        val isAddingRecipe: Boolean = false,
        val sortType: SortType = SortType.RECIPE_NAME
)
