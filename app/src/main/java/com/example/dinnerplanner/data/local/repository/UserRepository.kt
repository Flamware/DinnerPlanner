package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.entity.User
import com.example.dinnerplanner.data.local.database.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class UserRepository(private val userDao: UserDao) {
    val users: Flow<List<User>> = userDao.getAllUsers()

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    fun getUser(username: String, password: String): Flow<User?> {
        return userDao.getUser(username)
    }

    fun getUserById(id: Int): Flow<User> {
        return userDao.getUserById(id)
    }
    fun getUserId(username: String): Int {
        println("getUserId: ${userDao.getUserId(username)}")
        return userDao.getUserId(username)
    }

    suspend fun insertUser(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            userDao.insert(User(username = username, password = password))
            println("insertUser: $username")
            println("insertUser: $password")
        } else {
            throw IllegalArgumentException("Username and password must not be blank")
        }
    }

    suspend fun authenticate(username: String, password: String): User? {
        return userDao.getUser(username, password).firstOrNull()
    }
}