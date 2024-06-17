package com.example.dinnerplanner.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinnerplanner.data.local.database.entity.Like
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(like: Like)

    @Query("DELETE FROM likes WHERE userId = :userId AND recipeId = :recipeId")
    suspend fun deleteLike(userId: Int, recipeId: Int)

    @Query("SELECT COUNT(*) FROM likes WHERE recipeId = :recipeId")
    suspend fun countLikes(recipeId: Int): Int

    @Query("SELECT EXISTS(SELECT * FROM likes WHERE userId = :userId AND recipeId = :recipeId)")
    suspend fun isLiked(userId: Long, recipeId: Int): Boolean

    @Query("SELECT * FROM likes")
    fun getLikes(): Flow<List<Like>>

    @Query("INSERT INTO likes (recipeId, userId) VALUES (:recipeId, :userId)")
    suspend fun likeRecipe(recipeId: Long, userId: Int)

    @Query("DELETE FROM likes WHERE recipeId = :recipeId AND userId = :userId")
    suspend fun unlikeRecipe(recipeId: Long, userId: Int)
}