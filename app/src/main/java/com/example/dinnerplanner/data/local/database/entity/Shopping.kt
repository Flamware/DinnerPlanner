package com.example.dinnerplanner.data.local.database.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity;
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list",
    foreignKeys = [
        ForeignKey(entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE)
    ])
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "recipeId") val recipeId: Long,
    @ColumnInfo(name = "ingredientId") val ingredientId: Long,
    @ColumnInfo(name = "userId") val userId: Int
)