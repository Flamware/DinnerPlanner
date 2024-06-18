package com.example.dinnerplanner.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DatePicker
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import toBitmap
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.dinnerplanner.data.local.database.entity.Plan
import com.example.dinnerplanner.data.local.database.entity.Recipe
import java.time.LocalDate
import java.util.Calendar
@Composable
fun ShopScreen(viewModel: DinnerPlannerViewModel) {
    val shopViewModel = viewModel.shoppingListViewModel
    val userId = viewModel.authViewModel.currentUser.value!!.id

    // State to control the visibility of the basket dialog
    var showDialog by remember { mutableStateOf(false) }

    // State to hold plans and recipes with ingredients
    var plansWithRecipes by remember { mutableStateOf<Map<Plan, Pair<Recipe, List<Ingredient>>>>(emptyMap()) }

    // Collect shopping list items as state
    val shoppingListItems by viewModel.shoppingListViewModel.getAllShoppingListItemsByUserId(userId).collectAsState(initial = emptyList())

    // LaunchedEffect to fetch plans and recipes based on user ID
    LaunchedEffect(userId) {
        viewModel.viewModelScope.launch {
            val planList = viewModel.planViewModel.getAllPlansByUserId(userId).firstOrNull() ?: emptyList()
            val planWithRecipes = mutableMapOf<Plan, Pair<Recipe, List<Ingredient>>>()
            planList.forEach { plan ->
                val recipe = viewModel.recipeViewModel.getRecipeById(plan.recipeId).firstOrNull()
                recipe?.let {
                    val ingredients = viewModel.ingredientViewModel.getIngredientsByRecipeId(it.id).firstOrNull() ?: emptyList()
                    planWithRecipes[plan] = Pair(it, ingredients)
                }
            }
            plansWithRecipes = planWithRecipes
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
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.
                padding(bottom = 56.dp),
                onClick = {
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
                    .padding(bottom = 56.dp) // Add padding to avoid overlap
            ) {
                plansWithRecipes.forEach { (plan, recipeWithIngredients) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
                        ) {
                            Text(
                                text = "Day: ${plan.dayOfWeek} at ${plan.hourOfDay}",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(8.dp)
                            )

                            val recipe = recipeWithIngredients.first
                            Text(
                                text = "Recipe: ${recipe.title}",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp)
                            )
                            if (recipe.img != null) {
                                val imageBitmap = recipe.img.toBitmap().asImageBitmap()
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = "Recipe image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(8.dp)
                                )
                            }

                            val ingredients = recipeWithIngredients.second
                            Text(
                                text = "Ingredients:",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp)
                            )
                            ingredients.forEach { ingredient ->
                                val shoppingListItem by viewModel.shoppingListViewModel.getShoppingListByRecipeIdAndIngredientId(
                                    recipe.id,
                                    ingredient.id,
                                    userId
                                ).collectAsState(null)

                                val isInBasket = shoppingListItem != null

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = ingredient.name,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Button(
                                        onClick = {
                                            viewModel.viewModelScope.launch {
                                                if (isInBasket) {
                                                    shoppingListItem?.let {
                                                        viewModel.shoppingListViewModel.removeFromShoppingList(it.id)
                                                    }
                                                } else {
                                                    viewModel.shoppingListViewModel.addToShoppingList(
                                                        ShoppingListItem(recipeId = recipe.id, ingredientId = ingredient.id, userId = userId)
                                                    )
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
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Basket") },
            text = {
                if (shoppingListItems.isEmpty()) {
                    Text("Your basket is empty.")
                } else {
                    Column {
                        shoppingListItems.forEach { item ->
                            val recipe = plansWithRecipes.values.find { it.first.id == item.recipeId }?.first
                            val ingredient = plansWithRecipes.values.find { it.first.id == item.recipeId }?.second?.find { it.id == item.ingredientId }
                            Text("Recipe: ${recipe?.title}, Ingredient: ${ingredient?.name}")

                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Close")
                }
            }
        )
    }
}
