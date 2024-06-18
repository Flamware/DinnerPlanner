package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.ShoppingListItem
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import kotlinx.coroutines.launch
import toBitmap
import androidx.compose.runtime.collectAsState
import com.example.dinnerplanner.data.local.database.entity.Plan

@Composable
fun ShopScreen(viewModel: DinnerPlannerViewModel) {
    val shopViewModel = viewModel.shoppingListViewModel
    val userId = viewModel.authViewModel.currentUser.value!!.id

    // Observe recipesLiveData from RecipeViewModel
    val recipes by viewModel.recipeViewModel.recipesLiveData.observeAsState(emptyList())

    // State to control the visibility of the basket dialog
    var showDialog by remember { mutableStateOf(false) }

    // LaunchedEffect to fetch plans and recipes based on user ID
    LaunchedEffect(userId) {
        viewModel.planViewModel.getAllPlansByUserId(userId).collect { planList ->
            val recipeIds = planList.map { plan -> plan }
            println("Plan list: $planList")
            println("Recipe IDs: $recipeIds")
            // Fetch recipes based on the list of recipe IDs
            viewModel.recipeViewModel.getRecipesByIds(recipeIds.map { it.recipeId })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping List") },
                backgroundColor = MaterialTheme.colors.primary,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true // Show the basket dialog
            }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping Cart")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                println("Recipes to display: $recipes")
                items(recipes) { recipe ->

                    // text to display the recipe title
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .border(1.dp, Color.Gray, MaterialTheme.shapes.medium),

                        ) {
                        Text(
                            text = "Recipe: ${recipe.title}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(16.dp)
                        )
                        if (recipe.img != null) {
                            val imageBitmap = recipe.img.toBitmap().asImageBitmap()
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Recipe image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                        // Fetch ingredients for the recipe
                        val ingredients: List<Ingredient> by viewModel.ingredientViewModel.getIngredientsByRecipeId(
                            recipe.id
                        ).collectAsState(emptyList())
                        Text(
                            text = "Ingredients:",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(16.dp)
                        )
                        ingredients.forEach { ingredient ->
                            println("Ingredient: $ingredient")
                            val shoppingListItem by viewModel.shoppingListViewModel.getShoppingListByRecipeIdAndIngredientId(
                                recipe.id,
                                ingredient.id,
                                userId
                            ).collectAsState(null)

                            println("Shopping list item: $shoppingListItem")
                            val isInBasket = shoppingListItem != null


                            // Add the ingredient to the shopping list
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Ingredient: ${ingredient.name}",
                                )
                                Button(
                                    onClick = {
                                        viewModel.viewModelScope.launch {
                                            if (isInBasket) {
                                                // If the ingredient is already in the basket, remove it
                                                shoppingListItem?.let {
                                                    viewModel.shoppingListViewModel.removeFromShoppingList(it.id)
                                                }
                                            } else {
                                                // Otherwise, add it to the basket
                                                println("Adding to basket")
                                                viewModel.shoppingListViewModel.addToShoppingList(ShoppingListItem(recipeId = recipe.id, ingredientId = ingredient.id, userId = userId))
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = if (isInBasket) Color.Red else Color.Green)
                                ) {
                                    Text(if (isInBasket) "Remove from Basket" else "Add to Basket")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
