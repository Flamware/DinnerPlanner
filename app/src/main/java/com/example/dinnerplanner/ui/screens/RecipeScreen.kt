package com.example.dinnerplanner.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinnerplanner.AddRecipeDialog
import com.example.dinnerplanner.RecipeEvent
import com.example.dinnerplanner.RecipeState
import com.example.dinnerplanner.SortType
import com.example.dinnerplanner.ui.components.AddRecipeDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
        state: RecipeState,
        onEvent: (RecipeEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(RecipeEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add recipe"
                )
            }
        },
    ) { _ ->
        if(state.isAddingRecipe) {
            AddRecipeDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(RecipeEvent.SortRecipes(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(RecipeEvent.SortRecipes(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.recipes) { recipe ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${recipe.title}",
                            fontSize = 20.sp
                        )
                        Text(text = recipe.ingredients, fontSize = 12.sp)
                        Text(text = recipe.instructions, fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(RecipeEvent.DeleteRecipe(recipe))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete recipe"
                        )
                    }
                }
            }
        }
    }
}