package com.example.dinnerplanner.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dinnerplanner.data.local.database.dao.IngredientDao
import com.example.dinnerplanner.data.local.database.dao.LikeDao
import com.example.dinnerplanner.data.local.database.dao.PlanDao
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.database.dao.ShoppingListDao
import com.example.dinnerplanner.data.local.database.dao.UserDao
import com.example.dinnerplanner.data.local.database.entity.Ingredient
import com.example.dinnerplanner.data.local.database.entity.Like
import com.example.dinnerplanner.data.local.database.entity.Plan
import com.example.dinnerplanner.data.local.database.entity.ShoppingListItem

@Database(entities = [Recipe::class, User::class,Ingredient::class, Like::class, Plan::class, ShoppingListItem::class], version = 1, exportSchema = false)
abstract class CookItDB: RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val userDao: UserDao
    abstract val ingredientDao: IngredientDao
    abstract val likeDao: LikeDao
    abstract val planDao: PlanDao
    abstract val shopDao: ShoppingListDao

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