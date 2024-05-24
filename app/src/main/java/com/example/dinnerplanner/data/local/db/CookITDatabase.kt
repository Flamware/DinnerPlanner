package com.example.dinnerplanner.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dinnerplanner.data.local.dao.PlanningDao
import com.example.dinnerplanner.data.local.dao.RecipeDao
import com.example.dinnerplanner.data.local.dao.UserDao
import com.example.dinnerplanner.data.local.entity.Planning
import com.example.dinnerplanner.data.local.entity.Recipe
import com.example.dinnerplanner.data.local.entity.User

@Database(entities = [User::class, Recipe::class, Planning::class], version = 1)
abstract class CookITDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun planningDao(): PlanningDao
}
