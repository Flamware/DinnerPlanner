package com.example.dinnerplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dinnerplanner.ui.screens.*
import com.example.dinnerplanner.data.local.viewmodel.AuthViewModel
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel

@Composable
fun NavGraph(navController: NavHostController, isLoggedIn: MutableState<Boolean>, viewModel: DinnerPlannerViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Login.route) {
        composable(BottomNavItem.Login.route) {
            LoginScreen(authViewModel = viewModel.authViewModel, navController = navController)
        }
        composable(BottomNavItem.Home.route) { HomeScreen(viewModel = viewModel, navController = navController) }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}