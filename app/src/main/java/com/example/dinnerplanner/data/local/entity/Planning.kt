package com.example.dinnerplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plannings")
data class Planning(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val recipeId: Int,
    val date: String
)