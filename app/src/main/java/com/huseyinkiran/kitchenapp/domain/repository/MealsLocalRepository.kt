package com.huseyinkiran.kitchenapp.domain.repository

import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import kotlinx.coroutines.flow.Flow

interface MealsLocalRepository {

    suspend fun upsertMeal(meal: MealUIModel)

    suspend fun deleteMeal(meal: MealUIModel)

    fun getFavoriteMeals() : Flow<List<MealUIModel>>

    suspend fun getFavoriteMealsById(idMeal: String): MealUIModel?

}