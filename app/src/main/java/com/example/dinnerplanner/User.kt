package com.example.dinnerplanner
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    val username: String,
    val password: String
)