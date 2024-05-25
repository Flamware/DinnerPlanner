package com.example.dinnerplanner

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Recipe::class, User::class], version = 1)

abstract class CookItDB: RoomDatabase() {

    abstract val recipeDao: RecipeDao
    abstract val userDao: UserDao
}