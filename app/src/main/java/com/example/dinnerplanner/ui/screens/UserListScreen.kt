package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.data.local.database.entity.User
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.dinnerplanner.ui.components.UserSearch

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
fun UserListScreen(navController: NavController, viewModel: DinnerPlannerViewModel) {
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