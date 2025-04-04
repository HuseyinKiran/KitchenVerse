package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.repository.MealsLocalRepository
import com.huseyinkiran.kitchenapp.domain.repository.MealsRemoteRepository
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val localRepository: MealsLocalRepository,
    private val remoteRepository: MealsRemoteRepository
) : MealsRepository {

    override suspend fun upsertMeal(meal: MealUIModel) {
        localRepository.upsertMeal(meal)
    }

    override suspend fun deleteMeal(meal: MealUIModel) {
        localRepository.deleteMeal(meal)
    }

    override fun getFavoriteMeals(): Flow<List<MealUIModel>> {
        return localRepository.getFavoriteMeals()
    }

    override suspend fun getCategories(): List<CategoryUIModel> {
        return remoteRepository.getCategories()
    }

    override suspend fun getMealsFromCategory(category: String): List<MealUIModel> {
        return remoteRepository.getMealsFromCategory(category)
    }

    override suspend fun getMealsFromCountry(country: String): List<MealUIModel> {
        return remoteRepository.getMealsFromCountry(country)
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailUIModel {
        return remoteRepository.getMealDetails(idMeal)
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealUIModel? {
        return localRepository.getFavoriteMealsById(idMeal)
    }

}