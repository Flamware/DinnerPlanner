package com.example.dinnerplanner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.dinnerplanner.activity.DinnerPlanner
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.navigation.BottomNavItem
import com.example.dinnerplanner.ui.navigation.BottomNavigationBar
import com.example.dinnerplanner.ui.navigation.NavGraph
import com.example.dinnerplanner.ui.theme.DinnerPlannerTheme


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: DinnerPlannerViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as DinnerPlanner
        if (!application.isDatabaseInitialized()) {
            application.onCreate()
        }
        println("Database initialized: ${application.isDatabaseInitialized()}")
        viewModel = application.viewModel

        setContent {
            DinnerPlannerTheme {
                val navController = rememberNavController()
                val isLoggedIn = remember { mutableStateOf(false) }
                var currentScreen by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(currentScreen) { screen ->
                            currentScreen = screen
                            navController.navigate(screen.route)
                        }
                    }
                ) {
                    NavGraph(navController, isLoggedIn, viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DinnerPlannerTheme {
        Greeting("Android")
    }
}