package com.huseyinkiran.kitchenapp.domain.repository

import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel


interface MealsRemoteRepository {

    suspend fun getCategories(): List<CategoryUIModel>

    suspend fun getMealsFromCategory(category: String): List<MealUIModel>

    suspend fun getMealsFromCountry(country: String): List<MealUIModel>

    suspend fun getMealDetails(idMeal: String): MealDetailUIModel

}