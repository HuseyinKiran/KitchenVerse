package com.huseyinkiran.kitchenapp.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMealDao : MealDao {

    private val meals = mutableListOf<MealEntity>()
    private val mealsFlow = MutableStateFlow<List<MealEntity>>(emptyList())

    override suspend fun upsertMeal(meal: MealEntity) {
        meals.add(meal)
        mealsFlow.value = meals.toList()
    }

    override suspend fun deleteMeal(meal: MealEntity) {
        meals.remove(meal)
        mealsFlow.value = meals.toList()
    }

    override fun getFavoriteMeals(): Flow<List<MealEntity>> {
        return mealsFlow
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealEntity? {
        return meals.find { it.idMeal == idMeal }
    }
}