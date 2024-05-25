package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.RecipeList
import com.example.dinnerplanner.ui.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(viewModel: DinnerPlannerViewModel) {

    // State for managing the dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // State for managing the input fields in the dialog
    var recipeName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    val recipesFlow = viewModel.recipeViewModel.recipes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Add Recipe")
        }
        Spacer(modifier = Modifier.height(16.dp))
        RecipeList(recipes = recipesFlow)

        // Dialog for adding a new recipe
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Recipe") },
                text = {
                    Column {
                        TextField(
                            value = recipeName,
                            onValueChange = { recipeName = it },
                            label = { Text("Recipe Name") }
                        )
                        TextField(
                            value = ingredients,
                            onValueChange = { ingredients = it },
                            label = { Text("Ingredients") }
                        )
                        TextField(
                            value = instructions,
                            onValueChange = { instructions = it },
                            label = { Text("Instructions") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.recipeViewModel.upsertRecipe(
                                Recipe(
                                    userId = "1",
                                    title = recipeName,
                                    ingredients = ingredients,
                                    instructions = instructions
                                )
                            )
                            showDialog = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}