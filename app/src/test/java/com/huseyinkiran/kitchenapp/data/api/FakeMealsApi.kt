package com.huseyinkiran.kitchenapp.data.api

import com.huseyinkiran.kitchenapp.data.remote.api.MealsAPI
import com.huseyinkiran.kitchenapp.data.remote.model.CategoryResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealDetailResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealResponse

class FakeMealsApi : MealsAPI {

    private var categoryResponse = CategoryResponse(emptyList())
    private var mealResponse = MealResponse(emptyList())
    private var mealDetailResponse = MealDetailResponse(emptyList())

    fun setCategoryResponse(response: CategoryResponse) {
        categoryResponse = response
    }

    fun setMealResponse(response: MealResponse) {
        mealResponse = response
    }

    fun setMealDetailResponse(response: MealDetailResponse) {
        mealDetailResponse = response
    }

    override suspend fun getCategories(): CategoryResponse {
        return categoryResponse
    }

    override suspend fun getMealsFromCategory(category: String): MealResponse {
        return mealResponse
    }

    override suspend fun getMealsFromCountry(country: String): MealResponse {
        return mealResponse
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailResponse {
        return mealDetailResponse
    }
}