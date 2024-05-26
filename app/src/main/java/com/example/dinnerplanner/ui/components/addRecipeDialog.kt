package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.dinnerplanner.data.local.database.entity.Ingredient
@Composable
fun AddRecipeDialog(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var ingredients by remember { mutableStateOf(listOf(Ingredient(name = "", quantity = "0", recipeId = -1))) } // Initialize with an empty list"
    var newIngredientName by remember { mutableStateOf("") }
    var newIngredientQuantity by remember { mutableStateOf("") }

    // Function to handle adding ingredient
    fun addIngredient() {
        val newIngredient = Ingredient(name = newIngredientName, quantity = newIngredientQuantity, recipeId = -1)
        ingredients = ingredients + newIngredient
        newIngredientName = ""
        newIngredientQuantity = ""
    }

    AlertDialog(
        onDismissRequest = { onEvent(RecipeEvent.HideDialog) },
        title = { Text("Add Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(RecipeEvent.SetIngredients(ingredients))
                    onEvent(RecipeEvent.SaveRecipe)
                    ingredients = listOf(Ingredient(name = "", recipeId = -1, quantity = "0"))
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
                )
                // Map over the ingredients list to create a TextField for each item
                ingredients.forEachIndexed { index, currentIngredient ->
                    var ingredientName by remember { mutableStateOf(currentIngredient.name) }
                    var ingredientQuantity by remember { mutableStateOf(currentIngredient.quantity) }
                    Row {
                        TextField(
                            value = ingredientName,
                            onValueChange = { ingredientName = it },
                            placeholder = { Text("Ingredient name") },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        )
                        TextField(
                            value = ingredientQuantity,
                            onValueChange = { ingredientQuantity = it },
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