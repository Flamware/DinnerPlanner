package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.RecipeState

@Composable
fun AddRecipeDialog(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var currentIngredient by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<String>()) }
    var instructions by remember { mutableStateOf("") }

    // Function to handle adding ingredient
    fun addIngredient() {
        if (currentIngredient.isNotBlank()) {
            ingredients = ingredients + currentIngredient
            currentIngredient = ""
        }
    }

    AlertDialog(
        onDismissRequest = { onEvent(RecipeEvent.HideDialog) },
        title = { Text("Add Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    if (currentIngredient.isNotBlank()) {
                        addIngredient()
                    }
                    onEvent(RecipeEvent.SaveRecipe)
                    title = ""
                    ingredients = emptyList()
                    instructions = ""
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = { onEvent(RecipeEvent.HideDialog) }
            ) {
                Text("Cancel")
            }
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = state.recipeName,
                    onValueChange = { onEvent(RecipeEvent.SetRecipeName(it)) },
                    placeholder = { Text("Recipe name") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                TextField(
                    value = state.ingredients,
                    onValueChange = { onEvent(RecipeEvent.SetIngredients(it)) },
                    placeholder = { Text("Ingredients") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(onClick = { addIngredient() }) {
                    Text("Add Ingredient")
                }
                for (ingredient in ingredients) {
                    Text(ingredient)
                }
                TextField(
                    value = state.instructions,
                    onValueChange = { onEvent(RecipeEvent.SetInstructions(it)) },
                    placeholder = { Text("Instructions") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    )
}

@Preview
@Composable
fun AddRecipeDialogPreview() {
    AddRecipeDialog(
        state = RecipeState(),
        onEvent = { /* Mock action */ }
    )
}