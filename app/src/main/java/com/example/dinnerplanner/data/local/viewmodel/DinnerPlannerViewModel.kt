package com.example.dinnerplanner.data.local.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dinnerplanner.data.local.database.dao.PlanDao
import com.example.dinnerplanner.data.local.repository.UserRepository
import com.example.dinnerplanner.data.local.database.dao.RecipeDao
import com.example.dinnerplanner.data.local.repository.IngredientRepository
import com.example.dinnerplanner.data.local.repository.PlanRepository
import com.example.dinnerplanner.data.local.repository.RecipeRepository
import com.example.dinnerplanner.data.local.repository.ShoppingRepository
import com.example.dinnerplanner.ui.viewmodel.RecipeViewModel
import com.example.dinnerplanner.ui.viewmodel.ShopViewModel

class DinnerPlannerViewModel(
    application: Application,
    userRepository: UserRepository,
    recipeRepository: RecipeRepository,
    ingredientRepository: IngredientRepository,
    planRepository: PlanRepository,
    shopRepository: ShoppingRepository
)
    : AndroidViewModel(application) {
    val authViewModel = AuthViewModel(userRepository)
    val recipeViewModel = RecipeViewModel(recipeRepository)
    val ingredientViewModel = IngredientViewModel(ingredientRepository)
    val planViewModel = PlanViewModel(planRepository)
    val shoppingListViewModel = ShopViewModel(shopRepository)
}
