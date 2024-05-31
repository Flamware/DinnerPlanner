package com.example.dinnerplanner.ui.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dinnerplanner.ui.screens.*
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: DinnerPlannerViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Login.route) {
            LoginScreen(authViewModel = viewModel.authViewModel, navController = navController)
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(BottomNavItem.UserList.route) {
            UserListScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
        composable("userProfile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            if (userId != null) {
                UserProfileScreen(userId = userId, viewModel = viewModel)
            } else {
                // Handle error, for example navigate back to userList
                navController.navigate(BottomNavItem.UserList.route)
            }
        }
        composable(BottomNavItem.Planning.route) {
            PlanningScreen()
        }
    }
}