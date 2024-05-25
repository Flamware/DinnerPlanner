package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.data.local.database.entity.Recipe
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RecipeList(recipes: StateFlow<List<Recipe>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(recipes.value) { recipe ->
            RecipeItem(recipe = recipe)
        }
    }
}

@Preview
@Composable
fun RecipeListPreview() {
    val recipesFlow = remember {
        MutableStateFlow(
            listOf(
                Recipe(
                    userId = 1,
                    title = "Spaghetti Carbonara",
                    ingredients = "Spaghetti, eggs, bacon, parmesan cheese, black pepper",
                    instructions = "Cook spaghetti. Fry bacon. Mix eggs, cheese, and pepper. Combine all."
                ),
                Recipe(
                    userId = 1,
                    title = "Chicken Alfredo",
                    ingredients = "Fettuccine, chicken, heavy cream, parmesan cheese, garlic",
                    instructions = "Cook fettuccine. Cook chicken. Mix cream, cheese, and garlic. Combine all."
                ),
                Recipe(
                    userId = 1,
                    title = "Beef Stroganoff",
                    ingredients = "Beef, mushrooms, onions, sour cream, egg noodles",
                    instructions = "Cook beef. Cook mushrooms and onions. Mix sour cream. Combine all."
                )
            )
        )
    }

    RecipeList(recipes = recipesFlow)
}


