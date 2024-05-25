package com.example.dinnerplanner

sealed interface RecipeEvent {
    object SaveRecipe: RecipeEvent
    data class SetRecipeName(val recipeName: String): RecipeEvent
    data class SetIngredients(val ingredients: String): RecipeEvent
    data class SetInstructions(val instructions: String): RecipeEvent
    object ShowDialog: RecipeEvent
    object HideDialog: RecipeEvent
    data class SortRecipes(val sortType: SortType): RecipeEvent
    data class DeleteRecipe(val recipe: Recipe): RecipeEvent
}