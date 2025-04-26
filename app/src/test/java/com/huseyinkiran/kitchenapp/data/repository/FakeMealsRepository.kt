package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.api.FakeMealsApi
import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.data.remote.model.CategoryResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealDetailResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealResponse
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toCategoryDto
import com.huseyinkiran.kitchenapp.domain.model.toCategoryUI
import com.huseyinkiran.kitchenapp.domain.model.toEntity
import com.huseyinkiran.kitchenapp.domain.model.toMealDetailUI
import com.huseyinkiran.kitchenapp.domain.model.toMealDto
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class FakeMealsRepository(
    private val fakeApi: FakeMealsApi,
    private val fakeDao: FakeMealDao
) : MealsRepository {

    private var shouldThrowError = false

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }

    fun setCategories(categories: List<CategoryUIModel>) {
        fakeApi.setCategoryResponse(CategoryResponse(categories.map { it.toCategoryDto() }))
    }

    fun setMealsFromCategory(meals: List<MealUIModel>) {
        fakeApi.setMealResponse(MealResponse(meals.map { it.toMealDto() }))
    }

    fun setMealDetails(mealsDetail: List<MealDetailUIModel>) {
        fakeApi.setMealDetailResponse(MealDetailResponse(mealsDetail.map { it.toMealDto() }))
    }

    override suspend fun upsertMeal(meal: MealUIModel) {
        fakeDao.upsertMeal(meal.toEntity())
    }

    override suspend fun deleteMeal(meal: MealUIModel) {
        fakeDao.deleteMeal(meal.toEntity())
    }

    override fun getFavoriteMeals(): Flow<List<MealUIModel>> {
        return fakeDao.getFavoriteMeals().map { entityList ->
            entityList.map { mealEntity ->
                mealEntity.toMealUI()
            }
        }
    }

    override suspend fun getCategories(): List<CategoryUIModel> {
        if (shouldThrowError) throw Exception("Network Error")
        delay(10)
        return fakeApi.getCategories().categories.map { it.toCategoryUI() }
    }

    override suspend fun getMealsFromCategory(category: String): List<MealUIModel> {
        if (shouldThrowError) throw Exception("Network Error")
        delay(10)
        return fakeApi.getMealsFromCategory(category).meals.map { it.toMealUI() }
    }

    override suspend fun getMealsFromCountry(country: String): List<MealUIModel> {
        return fakeApi.getMealsFromCountry(country).meals.map { it.toMealUI() }
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailUIModel {
        if (shouldThrowError) throw Exception("Network Error")
        return fakeApi.getMealDetails(idMeal).meals.first().toMealDetailUI()
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealUIModel? {
        return fakeDao.getFavoriteMealsById(idMeal)?.toMealUI()
    }
}