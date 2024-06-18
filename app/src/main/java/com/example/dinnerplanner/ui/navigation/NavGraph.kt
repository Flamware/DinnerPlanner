package com.example.dinnerplanner.ui.navigation

import PlanningScreen
import ProfileScreen
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.ui.screens.*
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.RecipeItem
import kotlinx.coroutines.flow.Flow
@Composable
fun NavGraph(navController: NavHostController, viewModel: DinnerPlannerViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Login.route) {
            LoginScreen(authViewModel = viewModel.authViewModel, navController = navController)
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable("recipe/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()

            if (recipeId == null) {
                navController.navigate(BottomNavItem.Home.route)
                return@composable
            }

            var recipe by remember { mutableStateOf<Recipe?>(null) }
            var ingredients by remember { mutableStateOf<Flow<List<Ingredient>>?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(recipeId) {
                recipe = viewModel.recipeViewModel.recipeById(recipeId)
                ingredients = viewModel.ingredientViewModel.ingredientsByRecipeId(recipeId)
                isLoading = false
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                if (recipe != null && ingredients != null) {
                    RecipeItem(
                        recipe = recipe!!,
                        ingredientsFlow = ingredients!!,
                        viewModel = viewModel
                    )
                } else {
                    println("Recipe not found")
                    navController.navigate(BottomNavItem.Home.route)
                }
            }
        }

        composable(BottomNavItem.Search.route) {
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable("userProfile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            if (userId != null) {
                UserProfileScreen(userId = userId, viewModel = viewModel, navController = navController)
            } else {
                // Handle error, for example navigate back to userList
                navController.navigate(BottomNavItem.Profile.route)
            }
        }

        composable(BottomNavItem.Planning.route) {
            PlanningScreen(viewModel = viewModel, navController = navController)
        }
        composable(BottomNavItem.Shop.route) {
            ShopScreen(viewModel = viewModel)
        }
    }
}
