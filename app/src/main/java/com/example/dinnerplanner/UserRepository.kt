package com.example.dinnerplanner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class UserRepository(private val userDao: UserDao) {

    fun getUser(username: String, password: String): Flow<User?> {
        return userDao.getUser(username)
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun authenticate(username: String, password: String): Boolean {
        return getUser(username, password).firstOrNull()?.password == password
    }
}
