package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.viewmodel.AuthViewModel
import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import com.example.dinnerplanner.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeSearch(viewModel: DinnerPlannerViewModel, navController: NavController) {
    var searchText by remember { mutableStateOf("") }

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

        val searchResults: List<Recipe> by viewModel.recipeViewModel.searchResults.observeAsState(initial = emptyList())
        LazyColumn {
            items(searchResults) { recipe ->
                Button(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth(),
                    onClick = { navController.navigate("recipe/${recipe.id}") }
                ) {
                    Text(text = "${recipe.title}")
                }
            }
        }
    }
}