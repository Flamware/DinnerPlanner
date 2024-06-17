package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.RecipeList
import androidx.navigation.NavController

@Composable
fun UserProfileScreen(userId: Int, viewModel: DinnerPlannerViewModel, navController: NavController) {
    // Fetch the user's data from the ViewModel
    val userState by viewModel.authViewModel.getUserById(userId).collectAsState(initial = null)
    val recipesFlow = viewModel.recipeViewModel.getAllRecipesByUserId(userId)
    val recipeState by viewModel.recipeViewModel.recipes.collectAsState(initial = null)

    println("Loading recipes for user with id: $userId")
    println("recipesFlow: $recipesFlow")
    // Create a local copy of user
    val user = userState

    // Display the user's data
    if (user != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.navigate("search_recipe") }) {
                Text("Search Recipe")
            }
            Button(onClick = { navController.navigate("search_user") }) {
                Text("Search User")
            }
            Text(text = "Profile de : ${user.username}", fontSize = 20.sp)
            RecipeList(recipes = recipesFlow, viewModel = viewModel)
        }
    } else {
        Text(text = "User not found", fontSize = 20.sp)
    }
}