package com.example.dinnerplanner.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Login : BottomNavItem("login", Icons.Default.Person, "Login")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object UserList : BottomNavItem("user_list", Icons.Default.Search, "All Users")
    object Planning : BottomNavItem("planning", Icons.Default.Favorite, "Planning")
}
