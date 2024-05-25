package com.example.dinnerplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddRecipeDialog(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(RecipeEvent.HideDialog)
        },
        title = { Text(text = "Add recipe") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.recipeName,
                    onValueChange = {
                        onEvent(RecipeEvent.SetRecipeName(it))
                    },
                    placeholder = {
                        Text(text = "Recipe name")
                    }
                )
                TextField(
                    value = state.ingredients,
                    onValueChange = {
                        onEvent(RecipeEvent.SetIngredients(it))
                    },
                    placeholder = {
                        Text(text = "Ingredients")
                    }
                )
                TextField(
                    value = state.instructions,
                    onValueChange = {
                        onEvent(RecipeEvent.SetInstructions(it))
                    },
                    placeholder = {
                        Text(text = "Instructions")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(RecipeEvent.SaveRecipe)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}