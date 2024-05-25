package com.example.dinnerplanner.data.local.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dinnerplanner.data.local.repository.UserRepository
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import com.example.dinnerplanner.ui.viewmodel.RecipeViewModel

class DinnerPlannerViewModel(application: Application, userRepository: UserRepository, recipeRepository: RecipeRepository) : AndroidViewModel(application) {
    val authViewModel = AuthViewModel(userRepository)
    val recipeViewModel = RecipeViewModel(recipeRepository)
}