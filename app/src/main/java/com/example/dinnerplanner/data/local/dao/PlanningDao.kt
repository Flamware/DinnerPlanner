package com.example.dinnerplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dinnerplanner.data.local.entity.Planning
@Dao
interface PlanningDao {
    @Insert
    suspend fun insert(planning: Planning)

    @Update
    suspend fun update(planning: Planning)

    @Delete
    suspend fun delete(planning: Planning)

    @Query("SELECT * FROM plannings WHERE id = :id")
    suspend fun getPlanningById(id: Int): Planning?

    @Query("SELECT * FROM plannings")
    suspend fun getAllPlannings(): List<Planning>
}