package com.example.dinnerplanner.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    currentItem: BottomNavItem,
    onItemSelect: (BottomNavItem) -> Unit
) {
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = currentItem == item,
                onClick = { onItemSelect(item) }
            )
        }
    }
}


