import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.dinnerplanner.R
import com.example.dinnerplanner.data.local.database.entity.Plan
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.components.DayItem
import com.example.dinnerplanner.ui.screens.SearchRecipe
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import com.example.dinnerplanner.Fond

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
@Composable
fun PlanningScreen(viewModel: DinnerPlannerViewModel, navController: NavController) {
    val daysOfWeek = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    var selectedDay by remember { mutableStateOf(daysOfWeek[0]) }
    val hoursOfDay = (0..23 step 2).map { "$it:00" }
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    val planViewModel = viewModel.planViewModel
    val context = LocalContext.current

    // State to hold the plans for the selected day and hour
    var plans by remember { mutableStateOf<List<Plan>>(emptyList()) }

    var hourToRecipeMap by remember { mutableStateOf<Map<String, Recipe?>>(emptyMap()) }


    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Planning", style = MaterialTheme.typography.h6) },
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        ) { innerPadding ->
            Fond()
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Select a Day", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow() {
                        items(daysOfWeek) { day ->
                            DayItem(day = day, isSelected = day == selectedDay) {
                                selectedDay = it
                            }
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(hoursOfDay) { hour ->
                            Text(
                                text = hour,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedHour = hour
                                    },
                                textAlign = TextAlign.Center
                            )
                            Divider(color = Color.Black)
                                //color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f))
                            Spacer(modifier = Modifier.height(8.dp))
                            hourToRecipeMap[hour]?.let { recipe ->
                                Column(
                                    modifier = Modifier
                                        .clickable { navController.navigate("recipe/${recipe.id}") }
                                        .fillMaxWidth()
                                ) {
                                    Text(text = recipe.mealType)
                                    Text(
                                        text = recipe.title,
                                        style = MaterialTheme.typography.body1
                                    )
                                    if (recipe.img != null) {
                                        val imageBitmap = recipe.img.toBitmap().asImageBitmap()
                                        Image(
                                            bitmap = imageBitmap,
                                            contentDescription = "Recipe image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    } else {
                                        val mealTypeImage = when (recipe.mealType) {
                                            "BREAKFAST" -> R.drawable.default_breakfast
                                            "LUNCH" -> R.drawable.default_launch
                                            "DINNER" -> R.drawable.default_dinner
                                            "OTHER" -> R.drawable.default_snack
                                            else -> R.drawable.default_launch // Replace with your default image
                                        }
                                        Image(
                                            painter = painterResource(id = mealTypeImage),
                                            contentDescription = "Meal type image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                }
            }
        }

        if (selectedHour != null) {
            AlertDialog(
                onDismissRequest = { selectedHour = null },
                title = { Text(text = "Plan for $selectedDay at $selectedHour") },
                text = {
                    Column {
                        Text("Select a recipe to add to the plan")
                        SearchRecipe(
                            navController = navController,
                            viewModel = viewModel
                        ) { recipe ->
                            viewModel.viewModelScope.launch {
                                val userId = viewModel.authViewModel.currentUser.value?.id
                                if (userId != null) {
                                    planViewModel.addOrUpdatePlan(
                                        Plan(
                                            dayOfWeek = selectedDay,
                                            hourOfDay = selectedHour!!,
                                            recipeId = recipe.id,
                                            userId = userId
                                        )
                                    )
                                    selectedHour = null
                                } else {
                                    // Handle the case where currentUser.value is null
                                    SnackbarHostState().showSnackbar(message = "User ID not available")
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        selectedHour = null
                    }) {
                        Text("Close")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        viewModel.viewModelScope.launch {
                            val userId = viewModel.authViewModel.currentUser.value?.id
                            if (userId != null) {
                                planViewModel.deletePlanAthourAndDay(selectedDay, selectedHour!!, userId)
                                selectedHour = null
                                selectedRecipe = null

                                // Refresh plans
                                planViewModel.getPlansForDay(selectedDay, userId).collect { fetchedPlans ->
                                    plans = fetchedPlans

                                    // Clear the hourToRecipeMap and fill it with the updated plans
                                    hourToRecipeMap = emptyMap()
                                    fetchedPlans.forEach { plan ->
                                        val fetchedRecipe = viewModel.recipeViewModel.recipeById(plan.recipeId)
                                        fetchedRecipe?.let { recipe ->
                                            hourToRecipeMap = hourToRecipeMap + (plan.hourOfDay to recipe)
                                        }
                                    }
                                }
                            } else {
                                // Handle the case where currentUser.value is null
                                SnackbarHostState().showSnackbar(message = "User ID not available")
                            }
                        }
                    }) {
                        Text("Delete Plan")
                    }
                }
            )
        }
    }

    LaunchedEffect(selectedDay) {
        viewModel.viewModelScope.launch {
            // Fetch all plans for the selected day
            println("Fetching plans for $selectedDay")

            planViewModel.getPlansForDay(selectedDay, viewModel.authViewModel.currentUser.value?.id!!).collect { fetchedPlans ->
                println("Fetched plans: $fetchedPlans")
                plans = fetchedPlans

                // Map each plan to its hour and recipe
                fetchedPlans.forEach { plan ->
                    val fetchedRecipe = viewModel.recipeViewModel.recipeById(plan.recipeId)
                    fetchedRecipe?.let { recipe ->
                        hourToRecipeMap = hourToRecipeMap + (plan.hourOfDay to recipe)
                    }
                }
                println("Plans: $plans")
            }
        }
    }
}
