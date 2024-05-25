package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddRecipeDialog(
    onAddRecipe: (title: String, ingredients: List<String>, instructions: String) -> Unit,
    onDismiss: () -> Unit
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

    // Function to handle adding recipe
    fun addRecipe() {
        if (currentIngredient.isNotBlank()) {
            addIngredient()
        }
        onAddRecipe(title, ingredients, instructions)
        // Clear fields after adding recipe
        title = ""
        ingredients = emptyList()
        instructions = ""
        onDismiss() // Dismiss the dialog
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    addRecipe()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                // Add ingredients
                ingredients.forEachIndexed { index, ingredient ->
                    Text(
                        text = "Ingredient ${index + 1}: $ingredient",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                OutlinedTextField(
                    value = currentIngredient,
                    onValueChange = { currentIngredient = it },
                    label = { Text("Add Ingredient") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { addIngredient() },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Add Ingredient")
                }
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
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
        onAddRecipe = { _, _, _ -> /* Mock action */ },
        onDismiss = { /* Mock action */ }
    )
}
