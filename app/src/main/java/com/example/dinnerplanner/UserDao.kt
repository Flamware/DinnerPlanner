package com.example.dinnerplanner
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username: String): Flow<User?>

    // You can have more methods for other operations like updating, deleting users
}
