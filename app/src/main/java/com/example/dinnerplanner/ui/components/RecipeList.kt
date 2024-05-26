package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RecipeList(recipes: StateFlow<List<Recipe>>, ingredients: List<Ingredient>) {
    LazyColumn(
        modifier = Modifier
            .padding(3.dp)
    ) {
        items(recipes.value) { recipe ->
            RecipeItem(recipe = recipe, ingredients = ingredients)
        }
    }
}

@Preview
@Composable
fun PreviewRecipeList() {
    val recipes = remember {
        MutableStateFlow(
            listOf(
                Recipe(
                    userId = 1,
                    title = "Recipe 1",
                    instructions = "Instructions 1"
                ),
                Recipe(
                    userId = 1,
                    title = "Recipe 2",
                    instructions = "Instructions 2"
                )
            )
        )
    }
    val ingredients = listOf(
        Ingredient(name = "Ingredient 1", quantity = "1", recipeId = -1),
        Ingredient(name = "Ingredient 2", quantity = "2", recipeId = -1)
    )
    RecipeList(recipes, ingredients)
}