package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.database.entity.Ingredient
@Composable
fun RecipeItem(recipe: Recipe, ingredients: List<Ingredient>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable { /* TODO: Handle item click */ },

        elevation = CardDefaults.cardElevation(2.dp)  // Use CardDefaults.cardElevation for elevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            for (ingredient in ingredients) {
                Text(text = "${ingredient.name} - ${ingredient.quantity}", fontSize = 14.sp)
            }
            Text(text = recipe.instructions, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun PreviewRecipeItem() {
    val recipe = Recipe(
        userId = 1,
        title = "Recipe 1",
        instructions = "Instructions 1"
    )
    val ingredients = listOf(
        Ingredient(name = "Ingredient 1", quantity = "1", recipeId = -1),
        Ingredient(name = "Ingredient 2", quantity = "2", recipeId = -1)
    )
    RecipeItem(recipe, ingredients)
}