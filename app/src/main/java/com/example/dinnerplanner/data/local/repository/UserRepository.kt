package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.database.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class UserRepository(private val userDao: UserDao) {
    val users: Flow<List<User>> = userDao.getAllUsers()
    //print all users
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
    fun getUser(username: String, password: String): Flow<User?> {
        return userDao.getUser(username)
    }

    suspend fun insertUser(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            val id = UUID.randomUUID().toString() // Generate a unique ID
            userDao.insert(User(id, username, password))
            println("insertUser: $id")
            println("insertUser: $username")
            println("insertUser: $password")
        } else {
            throw IllegalArgumentException("Username and password must not be blank")
        }
    }
    suspend fun authenticate(username: String, password: String): Boolean {
        val user = userDao.getUser(username, password).firstOrNull()
        println("authenticate: $user")
        return user != null
        }
    }