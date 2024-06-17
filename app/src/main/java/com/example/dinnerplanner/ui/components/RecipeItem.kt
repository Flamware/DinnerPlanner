package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun RecipeItem(
    recipe: Recipe,
    ingredientsFlow: Flow<List<Ingredient>>,
    viewModel: DinnerPlannerViewModel
) {
    val ingredients by ingredientsFlow.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val currentUser = viewModel.authViewModel.currentUser.value
    var isLiked by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = recipe.id) {
        // Check if the recipe is liked by the current user
        isLiked = viewModel.recipeViewModel.isRecipeLiked(recipe.id, currentUser?.id ?: -1) as Boolean
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            for (ingredient in ingredients) {
                Text(text = "${ingredient.name} - ${ingredient.quantity}", fontSize = 14.sp)
            }
            Text(text = recipe.instructions, fontSize = 14.sp)
            Text(text = "Created by ${recipe.author}", fontSize = 14.sp)

            // Render like button with filled or unfilled heart icon based on isLiked state
            if (currentUser != null && currentUser.id != -1) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (isLiked) {
                            viewModel.recipeViewModel.unlikeRecipe(recipe.id, currentUser.id)
                        } else {
                            viewModel.recipeViewModel.likeRecipe(recipe.id, currentUser.id)
                        }
                        isLiked = !isLiked
                    }
                }) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like"
                    )
                }
            }
        }
    }
}
