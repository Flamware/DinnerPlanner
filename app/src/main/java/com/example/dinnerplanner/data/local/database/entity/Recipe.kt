package com.example.dinnerplanner.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE // Optional: Define onDelete behavior
    )]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String, // Foreign key referencing User entity
    val title: String,
    val ingredients: String,
    val instructions: String
) {
}