package com.example.dinnerplanner.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel

@Composable
fun RecipeSearch(
    viewModel: DinnerPlannerViewModel,
    navController: NavController,
    onRecipeClick: (Recipe) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedMealType by remember { mutableStateOf("") }

    Column {
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                viewModel.recipeViewModel.searchRecipes(newText)
            },
            label = { Text("Search Recipe") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        // Dropdown or buttons for selecting meal type
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Breakfast", "Lunch", "Dinner").forEach { type ->
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedMealType = type
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMealType == type) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(type)
                }
            }
        }

        // Observe search results using collectAsState properly
        val searchResults by viewModel.recipeViewModel.searchResults.collectAsState()

        LazyColumn {
            items(searchResults) { recipe ->
                // Check if the selected meal type matches the recipe's meal type
                if (selectedMealType.isEmpty() || recipe.mealType.equals(selectedMealType, ignoreCase = true)) {
                    Button(
                        onClick = {
                            onRecipeClick(recipe) // Invoke the callback to notify the selection
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        val imageBitmap = recipe.img?.toBitmap()?.asImageBitmap()
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Recipe Image",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Text(text = "${recipe.title}")
                    }
                }
            }
        }
    }
}
