package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.AddRecipeDialog
import com.example.dinnerplanner.ui.components.RecipeList
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.RecipeState
@Composable
fun HomeScreen(viewModel: DinnerPlannerViewModel, navController: NavController) {

    // State for managing the dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // State for managing the input fields in the dialog
    var recipeState by remember { mutableStateOf(RecipeState()) }

    val recipesFlow = viewModel.recipeViewModel.recipes

    val userId = viewModel.authViewModel.userId.observeAsState(initial = -1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            if (userId.value != -1) {
                showDialog = true
            }
            else {
                // redirect to login screen
                navController.navigate("login") // Replace "login" with the route of your login screen
            }
        }) {
                Text(text = "Add Recipe")
        }
        Spacer(modifier = Modifier.height(16.dp))

        RecipeList(recipes = recipesFlow) // Pass the StateFlow<List<Recipe>>

        // Dialog for adding a new recipe
        if (showDialog) {
            AddRecipeDialog(
                state = recipeState,
                onEvent = { event ->
                    when (event) {
                        is RecipeEvent.SetRecipeName -> recipeState = recipeState.copy(recipeName = event.recipeName)
                        is RecipeEvent.SetIngredients -> recipeState = recipeState.copy(ingredients = event.ingredients)
                        is RecipeEvent.SetInstructions -> recipeState = recipeState.copy(instructions = event.instructions)
                        is RecipeEvent.SaveRecipe -> {
                            println("userId: ${userId.value}")
                            println("recipeState: $recipeState")
                            println("recipeState.recipeName: ${recipeState.recipeName}")
                            println("recipeState.ingredients: ${recipeState.ingredients}")
                            println("recipeState.instructions: ${recipeState.instructions}")

                            viewModel.recipeViewModel.upsertRecipe(
                                Recipe(
                                    userId = userId.value, // Use the collected userId value
                                    title = recipeState.recipeName,
                                    ingredients = recipeState.ingredients,
                                    instructions = recipeState.instructions
                                )
                            )
                            showDialog = false
                        }
                        is RecipeEvent.HideDialog -> showDialog = false
                        is RecipeEvent.DeleteRecipe -> { /* Handle DeleteRecipe event here */ }
                        is RecipeEvent.ShowDialog -> { /* Handle ShowDialog event here */ }
                        is RecipeEvent.SortRecipes -> { /* Handle SortRecipes event here */ }
                    }
                }
            )
        }
    }
}