package com.example.dinnerplanner.activity

import android.app.Application
import com.example.dinnerplanner.data.local.database.CookItDB
import com.example.dinnerplanner.data.local.repository.IngredientRepository
import com.example.dinnerplanner.data.local.repository.PlanRepository
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import com.example.dinnerplanner.data.local.repository.UserRepository
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import dagger.hilt.android.HiltAndroidApp

class DinnerPlanner : Application() {
    lateinit var viewModel: DinnerPlannerViewModel

    override fun onCreate() {
        super.onCreate()

        val userDao = CookItDB.getDatabase(this).userDao
        val recipeDao = CookItDB.getDatabase(this).recipeDao
        val ingredientDao = CookItDB.getDatabase(this).ingredientDao
        val likeDao = CookItDB.getDatabase(this).likeDao
        val planDao = CookItDB.getDatabase(this).planDao

        val userRepository = UserRepository(userDao)
        val recipeRepository = RecipeRepository(recipeDao, likeDao)
        val ingredientRepository = IngredientRepository(ingredientDao)
        val planRepository = PlanRepository(planDao)

        viewModel = DinnerPlannerViewModel(this, userRepository, recipeRepository, ingredientRepository, planRepository)
    }

    fun isDatabaseInitialized(): Boolean {
        return this::viewModel.isInitialized
    }
}