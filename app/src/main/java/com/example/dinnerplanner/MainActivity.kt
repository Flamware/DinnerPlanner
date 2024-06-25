package com.example.dinnerplanner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                var currentScreen by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
                // Observe userId LiveData
                val userId by viewModel.authViewModel.currentUser.observeAsState()
                val isLoggedIn = userId != null


                val items = if (isLoggedIn) {
                    println("User is logged in")
                    listOf(BottomNavItem.Home, BottomNavItem.Search,BottomNavItem.Planning,BottomNavItem.Shop, BottomNavItem.Profile)
                } else {
                    println("User is not logged in")
                    listOf(BottomNavItem.Login, BottomNavItem.Search, BottomNavItem.Home)
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = items,
                            currentItem = currentScreen,
                            onItemSelect = { screen ->
                                currentScreen = screen
                                navController.navigate(screen.route)
                            }
                        )
                    }
                ) {
                    NavGraph(navController = navController, viewModel = viewModel)
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

@Composable
fun Fond(modifier: Modifier = Modifier){}
