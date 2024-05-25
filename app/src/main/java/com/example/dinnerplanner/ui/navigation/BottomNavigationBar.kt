package com.example.dinnerplanner.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(currentItem: BottomNavItem, onItemSelect: (BottomNavItem) -> Unit) {
    BottomNavigation {
        val navItems = listOf(BottomNavItem.Login,BottomNavItem.Home, BottomNavItem.Profile)
        navItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = item == currentItem,
                onClick = { onItemSelect(item) },
                alwaysShowLabel = false
            )
        }
    }
}

