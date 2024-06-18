package com.example.dinnerplanner.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dinnerplanner.data.local.database.entity.Plan
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Update
    suspend fun updatePlan(plan: Plan)
    @Insert
    suspend fun insertPlan(plan: Plan)

    @Query("SELECT * FROM plans")
    fun getAllPlans(): Flow<List<Plan>>

    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun deletePlan(id: Long)

    @Query("SELECT * FROM plans WHERE dayOfWeek = :dayOfWeek AND hourOfDay = :hourOfDay AND userId = :userId LIMIT 1")
    suspend fun getPlan(dayOfWeek: String, hourOfDay: String, userId: Int): Plan?

    @Query("SELECT * FROM plans WHERE dayOfWeek = :selectedDay AND hourOfDay = :hour AND userId = :userId")
    fun getPlansForDayAndHour(selectedDay: String, hour: String, userId: Int): Flow<List<Plan>>

    @Query("SELECT * FROM plans WHERE dayOfWeek = :selectedDay AND userId = :userId")
    fun getPlansForDay(selectedDay: String, userId: Int): Flow<List<Plan>>

    @Query("DELETE FROM plans WHERE dayOfWeek = :selectedDay AND hourOfDay = :selectedHour AND userId = :userId")
    suspend fun deletePlanAthourAndDay(selectedDay: String, selectedHour: String, userId: Int)

    @Query("SELECT * FROM plans WHERE userId = :id")
    fun getAllPlansByUserId(id: Int): Flow<List<Plan>>

    @Query("SELECT * FROM plans WHERE userId = :userId AND dayOfWeek = :date")
    fun getPlansByDate(userId: Int, date: String): Flow<List<Plan>>
}

