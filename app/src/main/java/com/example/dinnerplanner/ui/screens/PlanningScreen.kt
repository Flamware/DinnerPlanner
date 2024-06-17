import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.dinnerplanner.data.local.database.entity.Plan
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.DayItem
import com.example.dinnerplanner.ui.screens.SearchRecipe
import kotlinx.coroutines.launch

@Composable
fun PlanningScreen(viewModel: DinnerPlannerViewModel) {
    val daysOfWeek = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    var selectedDay by remember { mutableStateOf(daysOfWeek[0]) }
    val hoursOfDay = (0..23 step 2).map { "$it:00" }
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    val navController = rememberNavController()

    val planViewModel = viewModel.planViewModel
    val context = LocalContext.current

    // State to hold the plans for the selected day and hour
    var plans by remember { mutableStateOf<List<Plan>>(emptyList()) }

    var hourToRecipeMap by remember { mutableStateOf<Map<String, Recipe?>>(emptyMap()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Planning") }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                Row {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(daysOfWeek) { day ->
                            DayItem(day = day, isSelected = day == selectedDay) {
                                selectedDay = it
                            }
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(hoursOfDay) { hour ->
                            Column {
                                Text(
                                    text = hourToRecipeMap[hour]?.title ?: hour,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            selectedHour = hour
                                        }
                                )

                                // Display recipe content if available for the selected hour
                                val recipe = hourToRecipeMap[hour]

                                if (recipe != null) {
                                    Text(
                                        text = recipe.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    )
                                }
                                    // Fetch the recipe if not already fetched
                                    LaunchedEffect(hour) {
                                        val plan = plans.find { it.hourOfDay == hour }
                                        println("Plan for $hour: $plan")
                                        plan?.let {
                                            println("Fetching recipe for plan: $plan")
                                            val fetchedRecipe = viewModel.recipeViewModel.recipeById(plan.recipeId)
                                            println("Fetched recipe: $fetchedRecipe")
                                            fetchedRecipe?.let {
                                                hourToRecipeMap = hourToRecipeMap + (hour to it)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(56.dp)) // Add space at the bottom equal to the height of the bottom navigation bar
            }

            if (selectedHour != null) {
                AlertDialog(
                    onDismissRequest = { selectedHour = null },
                    title = { Text(text = "Plan for $selectedDay at $selectedHour") },
                    text = {
                        Column {
                            Text("Select a recipe to add to the plan")
                            SearchRecipe(navController = navController, viewModel = viewModel) { recipe ->
                                selectedRecipe = recipe
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            selectedRecipe?.let {
                                viewModel.viewModelScope.launch {
                                    val userId = viewModel.authViewModel.currentUser.value?.id
                                    if (userId != null) {
                                        planViewModel.addOrUpdatePlan(
                                            Plan(
                                                dayOfWeek = selectedDay,
                                                hourOfDay = selectedHour!!,
                                                recipeId = selectedRecipe!!.id,
                                                userId = userId
                                            )
                                        )
                                        selectedHour = null
                                        selectedRecipe = null
                                    } else {
                                        // Handle the case where currentUser.value is null
                                        SnackbarHostState().showSnackbar(message = "User ID not available")
                                    }
                                }
                            }
                        }) {
                            Text("Validate")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            selectedHour = null
                            selectedRecipe = null
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
