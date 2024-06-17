package com.example.dinnerplanner.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.Plan
import com.example.dinnerplanner.data.local.database.entity.Recipe
import com.example.dinnerplanner.data.local.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class PlanViewModel (private val repository: PlanRepository) : ViewModel() {
    val plans: Flow<List<Plan>> = repository.getAllPlans()

    suspend fun insertPlan(selectedDay: String, selectedHour: String, it: Recipe, value: Int) {
        val plan = Plan(dayOfWeek = selectedDay, hourOfDay = selectedHour, recipeId = it.id, userId = value)
        repository.addOrUpdatePlan(plan)
    }

    fun getPlansForDayAndHour(selectedDay: String, hour: String, userId: Int): Flow<List<Plan>> {
        return repository.getPlansForDayAndHour(selectedDay, hour, userId)
    }

    fun getPlansForDay(selectedDay: String, userId: Int): Flow<List<Plan>> {
        return repository.getPlansForDay(selectedDay, userId)
    }

    fun addOrUpdatePlan(plan: Plan) {
        viewModelScope.launch {
            repository.addOrUpdatePlan(plan)
        }
    }

}
