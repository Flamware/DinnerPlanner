package com.example.dinnerplanner.data.local.repository

import com.example.dinnerplanner.data.local.database.dao.PlanDao
import com.example.dinnerplanner.data.local.database.entity.Plan
import kotlinx.coroutines.flow.Flow

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

}