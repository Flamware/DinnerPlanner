package com.example.dinnerplanner.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    OTHER
}
@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Int,
    val title: String,
    val instructions: String,
    val author: String,
    val mealType: String,
    val img: ByteArray?
)