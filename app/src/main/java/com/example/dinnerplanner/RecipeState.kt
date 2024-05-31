package com.example.dinnerplanner

import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.MealType
import com.example.dinnerplanner.data.local.database.entity.Recipe

data class RecipeState(
        val recipes: List<Recipe> = emptyList(),
        val recipeName: String = "",
        val ingredients: List<Ingredient> = emptyList(),
        val instructions: String = "",
        val mealType: String = MealType.LUNCH.name,
        val isAddingRecipe: Boolean = false,
        val sortType: SortType = SortType.RECIPE_NAME
)