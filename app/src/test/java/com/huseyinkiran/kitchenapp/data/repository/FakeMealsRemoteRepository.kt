package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.remote.api.MealsAPI
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toCategoryUI
import com.huseyinkiran.kitchenapp.domain.model.toMealDetailUI
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import com.huseyinkiran.kitchenapp.domain.repository.MealsRemoteRepository

class FakeMealsRemoteRepository(private val api: MealsAPI) : MealsRemoteRepository {

    override suspend fun getCategories(): List<CategoryUIModel> {
        return api.getCategories().categories.map { it.toCategoryUI() }
    }

    override suspend fun getMealsFromCategory(category: String): List<MealUIModel> {
        return api.getMealsFromCategory(category).meals.map { it.toMealUI() }
    }

    override suspend fun getMealsFromCountry(country: String): List<MealUIModel> {
        return api.getMealsFromCountry(country).meals.map { it.toMealUI() }
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailUIModel {
        return api.getMealDetails(idMeal).meals.first().toMealDetailUI()
    }
}