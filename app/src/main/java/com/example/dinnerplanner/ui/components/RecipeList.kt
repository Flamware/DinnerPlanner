package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.StateFlow
import com.example.dinnerplanner.data.local.viewmodel.IngredientViewModel
@Composable
fun RecipeList(recipes: StateFlow<List<Recipe>>, ingredientViewModel: IngredientViewModel) {
    val recipeList by recipes.collectAsState()
    LazyColumn(
    ) {
        items(recipeList) { recipe ->
            val ingredientsFlow = ingredientViewModel.getIngredientsForRecipe(recipe.id)
            RecipeItem(recipe = recipe, ingredientsFlow = ingredientsFlow)
        }
    }
}