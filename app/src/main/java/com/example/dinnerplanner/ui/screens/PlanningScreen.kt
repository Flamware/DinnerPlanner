package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dinnerplanner.ui.components.DayItem

@Composable
fun PlanningScreen() {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var selectedDay by remember { mutableStateOf(daysOfWeek[0]) }
    val hoursOfDay = List(24) { it.toString().padStart(2, '0') + ":00" } // List of hours in a day

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planning") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Plan")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                Row {
                    LazyColumn(modifier = Modifier
                        .padding(8.dp)) {
                        items(daysOfWeek) { day ->
                            DayItem(day = day, isSelected = day == selectedDay) {
                                selectedDay = it
                            }
                        }
                    }
                    LazyColumn {
                        items(hoursOfDay) { hour ->
                            Text(text = hour, modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp))
                        }
                    }
                    // Replace this with your list of planned meals
                    LazyColumn {
                        items(10) { index ->
                            Text(text = "Plan $index", modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(56.dp)) // Add space at the bottom equal to the height of the bottom navigation bar
            }
        }
    }
}

@Preview
@Composable
fun PlanningScreenPreview() {
    PlanningScreen()
}