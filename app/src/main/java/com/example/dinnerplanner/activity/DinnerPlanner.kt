package com.example.dinnerplanner.activity

import android.app.Application
import com.example.dinnerplanner.data.local.database.CookItDB
import com.example.dinnerplanner.data.local.repository.UserRepository
import com.example.dinnerplanner.data.local.viewmodel.DinnerPlannerViewModel
import dagger.hilt.android.HiltAndroidApp

class DinnerPlanner : Application() {
    lateinit var viewModel: DinnerPlannerViewModel

    override fun onCreate() {
        super.onCreate()

        val userDao = CookItDB.getDatabase(this).userDao
        val userRepository = UserRepository(userDao)

        val recipeDao = CookItDB.getDatabase(this).recipeDao

        viewModel = DinnerPlannerViewModel(this, userRepository, recipeDao)
    }

    fun isDatabaseInitialized(): Boolean {
        return this::viewModel.isInitialized
    }
}