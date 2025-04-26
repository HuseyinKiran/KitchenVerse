package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.remote.api.MealsAPI
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toCategoryUI
import com.huseyinkiran.kitchenapp.domain.model.toMealDetailUI
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import com.huseyinkiran.kitchenapp.domain.repository.MealsRemoteRepository
import javax.inject.Inject

class MealsRemoteRepositoryImpl @Inject constructor (
    private val api: MealsAPI
): MealsRemoteRepository {

    override suspend fun getCategories(): List<CategoryUIModel> {
        val response = api.getCategories()
        return response.categories.map { it.toCategoryUI() }
    }

    override suspend fun getMealsFromCategory(category: String): List<MealUIModel> {
        val response = api.getMealsFromCategory(category)
        return response.meals.map { it.toMealUI() }
    }

    override suspend fun getMealsFromCountry(country: String): List<MealUIModel> {
        val response = api.getMealsFromCountry(country)
        return response.meals.map { it.toMealUI() }
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailUIModel {
        val response = api.getMealDetails(idMeal)
        return response.meals.first().toMealDetailUI()
    }

}