package com.example.dinnerplanner

import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.Recipe

sealed interface RecipeEvent {
    object SaveRecipe: RecipeEvent
    data class SetRecipeName(val recipeName: String): RecipeEvent
    data class SetIngredients(val ingredients: List<Ingredient>): RecipeEvent
    data class SetInstructions(val instructions: String): RecipeEvent
    data class SetMealType(val mealType: String): RecipeEvent // New event for setting the meal type
    object ShowDialog: RecipeEvent
    object HideDialog: RecipeEvent
    data class SortRecipes(val sortType: SortType): RecipeEvent
    data class DeleteRecipe(val recipe: Recipe): RecipeEvent
}