package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: DinnerPlannerViewModel) {
    val authViewModel = viewModel.authViewModel
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Profile Screen")
    }
    Button(onClick = {
        // Handle logout
        authViewModel.logout()
        navController.navigate("login")
    }) {
        Text(text = "Logout")
    }
}
