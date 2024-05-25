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

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* TODO: Handle item click */ },
        elevation = CardDefaults.cardElevation(2.dp)  // Use CardDefaults.cardElevation for elevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = recipe.ingredients, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = recipe.instructions, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun RecipeItemPreview() {
    RecipeItem(
        Recipe(
            userId = 1,
            title = "Spaghetti Carbonara",
            ingredients = "Spaghetti, eggs, bacon, parmesan cheese, black pepper",
            instructions = "Cook spaghetti. Fry bacon. Mix eggs, cheese, and pepper. Combine all."
        )
    )
}
