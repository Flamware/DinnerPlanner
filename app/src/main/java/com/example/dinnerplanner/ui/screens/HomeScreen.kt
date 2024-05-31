package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.AddRecipeDialog
import com.example.dinnerplanner.ui.components.RecipeList
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.RecipeState
import com.example.dinnerplanner.ui.navigation.BottomNavItem
import com.example.dinnerplanner.ui.navigation.BottomNavigationBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: DinnerPlannerViewModel, navController: NavController) {
    // State for managing the dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // State for managing the input fields in the dialog
    var recipeState by remember { mutableStateOf(RecipeState()) }

    val recipesFlow = viewModel.recipeViewModel.recipes

    val session = viewModel.authViewModel.currentUser.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Use Modifier.weight(1f) to make LazyColumn take the available space
        Box(modifier = Modifier.weight(1f)) {
            RecipeList(recipes = recipesFlow, ingredientViewModel = viewModel.ingredientViewModel)
        }

        // Button to add a recipe
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 56.dp),
            onClick = {
                if (session.value != null) {
                    showDialog = true
                } else {
                    navController.navigate("login")
                }
            }
        ) {
            Text(text = "Add Recipe")
        }
    }

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
                        println("Saving recipe: $recipeState")
                        viewModel.viewModelScope.launch {
                            val recipe = Recipe(
                                userId = session.value?.id ?: -1,
                                title = recipeState.recipeName,
                                instructions = recipeState.instructions,
                                author = session.value?.username ?: ""
                            )
                            val recipeId = viewModel.recipeViewModel.insert(recipe)
                            println("Inserted recipe with id: $recipeId")
                            println("Ingredients to save: ${recipeState.ingredients}")
                            recipeState.ingredients.forEach { ingredient ->
                                println("Inserting ingredient: $ingredient")
                                viewModel.ingredientViewModel.upsertIngredient(ingredient.copy(recipeId = recipeId))
                            }
                            showDialog = false
                            recipeState = RecipeState()
                        }
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
