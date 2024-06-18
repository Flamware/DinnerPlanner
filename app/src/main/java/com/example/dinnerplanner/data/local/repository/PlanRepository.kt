package com.example.dinnerplanner.data.local.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dinnerplanner.data.local.database.dao.PlanDao
import com.example.dinnerplanner.data.local.database.entity.Plan
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class PlanRepository(private val planDao: PlanDao) {
    fun getAllPlans() = planDao.getAllPlans()
    suspend fun addOrUpdatePlan(plan: Plan) {
        val existingPlan = planDao.getPlan(plan.dayOfWeek, plan.hourOfDay, plan.userId)
        if (existingPlan != null) {
            // Update existing plan
            planDao.updatePlan(plan.copy(id = existingPlan.id)) // Make sure to retain the existing ID
        } else {
            // Insert new plan
            planDao.insertPlan(plan)
        }
    }
    fun getPlansForDayAndHour(selectedDay: String, hour: String, userId: Int): Flow<List<Plan>> {
        return planDao.getPlansForDayAndHour(selectedDay, hour, userId)
        }

    fun getPlansForDay(selectedDay: String, userId: Int): Flow<List<Plan>> {
        return planDao.getPlansForDay(selectedDay, userId)
    }

    suspend fun deletePlan(plan: Plan) {
        planDao.deletePlan(plan.id)
    }

    suspend fun deletePlanAthourAndDay(selectedDay: String, selectedHour: String, userId: Int) {
        planDao.deletePlanAthourAndDay(selectedDay, selectedHour, userId)
    }

    fun getAllPlansByUserId(id: Int): Flow<List<Plan>> {
        return planDao.getAllPlansByUserId(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPlansByDate(userId: Int, date: LocalDate): Flow<List<Plan>> {
        return planDao.getPlansByDate(userId, date.toString())
    }

}