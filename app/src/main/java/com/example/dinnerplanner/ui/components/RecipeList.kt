package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.StateFlow
import com.example.dinnerplanner.data.local.viewmodel.IngredientViewModel
@Composable
fun RecipeList(recipes: StateFlow<List<Recipe>>, ingredientViewModel: IngredientViewModel) {
    LazyColumn(
    ) {
        items(recipes.value) { recipe ->
            val ingredientsFlow = ingredientViewModel.getIngredientsForRecipe(recipe.id)
            RecipeItem(recipe = recipe, ingredientsFlow = ingredientsFlow)
        }
    }
}