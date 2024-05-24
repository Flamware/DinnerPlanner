package com.example.dinnerplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String, // Field to store the user ID associated with the recipe
    val name: String,
    val instructions: String
)
