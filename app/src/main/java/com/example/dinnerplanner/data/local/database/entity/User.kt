package com.example.dinnerplanner.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val password: String
)