package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.RecipeList
import com.example.dinnerplanner.ui.components.RecipeSearch
import com.example.dinnerplanner.ui.components.UserSearch

@Composable
fun SearchScreen(navController: NavController, viewModel: DinnerPlannerViewModel) {
    val searchType = remember { mutableStateOf("") }
    if (searchType.value == "user") {
        SearchUser(navController = navController, viewModel = viewModel)
    } else {
        SearchRecipe(navController = navController, viewModel = viewModel) { recipe ->
            navController.navigate("recipe/${recipe.id}")
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { searchType.value = "recipe"},
                    modifier = Modifier.padding(8.dp) // Add margin to the button
                ) {
                    Text("Search Recipe")
                }
                Button(
                    onClick = { searchType.value = "user" },
                    modifier = Modifier.padding(8.dp) // Add margin to the button
                ) {
                    Text("Search User")
                }
            }

        }
    }
}
@Composable
fun UserListItem(user: User, navController: NavController) {
    Button(
        onClick = { navController.navigate("userProfile/${user.id}") },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            text = "User: ${user.username}"
        )
    }
}

@Composable
fun SearchUser(navController: NavController, viewModel: DinnerPlannerViewModel) {
    val users by viewModel.authViewModel.users.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserSearch(viewModel = viewModel.authViewModel, navController = navController)
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)  // Add padding around LazyColumn
            ) {

                // Use items() to display each user
                items(users) { user ->
                    UserListItem(user = user, navController = navController)
                    Spacer(modifier = Modifier.height(8.dp)) // Add space between buttons
                }
            }
        }
    }
}

@Composable
fun RecipeList(recipe: Recipe, onRecipeClick: (Recipe) -> Unit) {
    Button(
        onClick = { onRecipeClick(recipe) },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            text = "Recipe: ${recipe.title}"  // Changed to show recipe title
        )
    }
}

@Composable
fun SearchRecipe(navController: NavController, viewModel: DinnerPlannerViewModel, onRecipeSelected: (Recipe) -> Unit) {
    val recipes by viewModel.recipeViewModel.recipesFlow.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RecipeSearch(viewModel = viewModel, navController = navController)
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)  // Add padding around LazyColumn
            ) {
                items(recipes) { recipe ->
                    RecipeList(recipe = recipe, onRecipeClick = onRecipeSelected)
                    Spacer(modifier = Modifier.height(8.dp)) // Add space between buttons
                }
            }
        }
    }
}


@Composable
fun SearchScreenPreview() {
    val navController = rememberNavController()
    lateinit var viewModel: DinnerPlannerViewModel
    SearchScreen(navController = navController, viewModel = viewModel)
}