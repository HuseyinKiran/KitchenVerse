package com.huseyinkiran.kitchenapp.domain.repository

import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import kotlinx.coroutines.flow.Flow

interface MealsRepository {

    suspend fun upsertMeal(meal: MealUIModel)

    suspend fun deleteMeal(meal: MealUIModel)

    fun getFavoriteMeals(): Flow<List<MealUIModel>>

    suspend fun getCategories(): List<CategoryUIModel>

    suspend fun getMealsFromCategory(category: String): List<MealUIModel>

    suspend fun getMealsFromCountry(country: String): List<MealUIModel>

    suspend fun getMealDetails(idMeal: String): MealDetailUIModel

    suspend fun getFavoriteMealsById(idMeal: String): MealUIModel?

}