package com.example.dinnerplanner.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.dao.UserDao

@Database(entities = [Recipe::class, User::class], version = 1)
abstract class CookItDB: RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: CookItDB? = null

        fun getDatabase(context: Context): CookItDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CookItDB::class.java,
                        "cook_it_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}