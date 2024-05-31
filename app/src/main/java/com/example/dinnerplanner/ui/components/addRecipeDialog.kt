package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.RecipeState
import com.example.dinnerplanner.data.local.database.entity.Ingredient

@Composable
fun AddRecipeDialog(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var ingredients by remember { mutableStateOf(state.ingredients) }

    // Function to handle adding an empty ingredient
    fun addIngredient() {
        ingredients = ingredients + Ingredient(name = "", quantity = "0", recipeId = -1)
    }

    AlertDialog(
        onDismissRequest = { onEvent(RecipeEvent.HideDialog) },
        title = { Text("Add Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    // Filter out ingredients with empty names
                    val validIngredients = ingredients.filter { it.name.isNotEmpty() }
                    onEvent(RecipeEvent.SetIngredients(validIngredients))
                    onEvent(RecipeEvent.SaveRecipe)
                    ingredients = listOf(Ingredient(name = "", recipeId = -1, quantity = "0"))
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onEvent(RecipeEvent.HideDialog) }) {
                Text("Cancel")
            }
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = state.recipeName,
                    onValueChange = { onEvent(RecipeEvent.SetRecipeName(it)) },
                    placeholder = { Text("Recipe name") },
                )
                ingredients.forEachIndexed { index, ingredient ->
                    var ingredientName by remember { mutableStateOf(ingredient.name) }
                    var ingredientQuantity by remember { mutableStateOf(ingredient.quantity) }
                    Row {
                        TextField(
                            value = ingredientName,
                            onValueChange = {
                                ingredientName = it
                                ingredients = ingredients.toMutableList().apply {
                                    this[index] = this[index].copy(name = ingredientName)
                                }
                            },
                            placeholder = { Text("Ingredient name") },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        )
                        TextField(
                            value = ingredientQuantity,
                            onValueChange = {
                                ingredientQuantity = it
                                ingredients = ingredients.toMutableList().apply {
                                    this[index] = this[index].copy(quantity = ingredientQuantity)
                                }
                            },
                            placeholder = { Text("Quantity") },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        )
                    }
                }
                Button(onClick = { addIngredient() }) {
                    Text("Add Ingredient")
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
        onEvent = { }
    )
}
