package com.example.dinnerplanner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerplanner.data.local.database.entity.ShoppingListItem
import com.example.dinnerplanner.data.local.repository.ShoppingRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ShopViewModel(private val repository: ShoppingRepository) : ViewModel() {
    private val _shoppingList = MutableLiveData<List<ShoppingListItem>>()
    val shoppingList: LiveData<List<ShoppingListItem>> = _shoppingList

    init {
        refreshShoppingList()
    }

    private fun refreshShoppingList() = viewModelScope.launch {
        _shoppingList.value = repository.getAllShoppingListItems()
    }

    fun addToShoppingList(item: ShoppingListItem) = viewModelScope.launch {
        repository.insert(item)
    }


    fun removeFromShoppingList(item: Int) = viewModelScope.launch {
        repository.delete(item)
    }


    suspend fun getShoppingListByRecipeId(recipeId: Long): List<ShoppingListItem> {
        return repository.getShoppingListByRecipeId(recipeId)
    }

    suspend fun getShoppingListByIngredientId(ingredientId: Long): List<ShoppingListItem> {
        return repository.getShoppingListByIngredientId(ingredientId)
    }

    suspend fun getAllShoppingListItems(): List<ShoppingListItem> {
        return repository.getAllShoppingListItems()
    }

    fun deleteAllShoppingListItems() {
        viewModelScope.launch {
            repository.deleteAllShoppingListItems()
        }
    }

    fun getShoppingListByUserId(userId: Int): Flow<List<ShoppingListItem>> {
        return repository.getShoppingListByUserId(userId)
    }

    fun getShoppingListByRecipeIdAndIngredientId(recipeId: Long, ingredientId: Long, userId: Int): Flow<ShoppingListItem?> {
        return repository.getShoppingListByRecipeIdAndIngredientId(recipeId, ingredientId, userId)
    }

    fun getAllShoppingListItemsByUserId(userId: Int): Flow<List<ShoppingListItem>> {
        return repository.getAllShoppingListItemsByUserId(userId)
    }
}