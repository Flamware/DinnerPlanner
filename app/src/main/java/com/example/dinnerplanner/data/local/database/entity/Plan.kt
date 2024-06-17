package com.example.dinnerplanner.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "plans",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Plan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dayOfWeek: String,
    val hourOfDay: String,
    val recipeId: Long, // Foreign key to Recipe
    val userId: Int // Foreign key to User
)
