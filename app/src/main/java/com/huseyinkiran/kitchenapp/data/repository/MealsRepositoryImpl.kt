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

    private var cachedCategories: List<CategoryUIModel> = emptyList()
    private var lastFetchTime: Long = 0
    private var cachedMealsByCategory: MutableMap<String, List<MealUIModel>> = mutableMapOf()
    private var lastFetchTimeByCategory: MutableMap<String, Long> = mutableMapOf()
    private var cachedMealDetailById: MutableMap<String, MealDetailUIModel> = mutableMapOf()
    private var lastFetchTimeById: MutableMap<String, Long> = mutableMapOf()
    private val cacheDuration = 5 * 60 * 1000

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
        val currentTime = System.currentTimeMillis()
        return if ((currentTime - lastFetchTime) < cacheDuration && cachedCategories.isNotEmpty()) {
            cachedCategories
        } else {
            val categoriesFromApi = remoteRepository.getCategories()
            cachedCategories = categoriesFromApi
            lastFetchTime = currentTime
            categoriesFromApi
        }
    }

    override suspend fun getMealsFromCategory(category: String): List<MealUIModel> {
        val currentTime = System.currentTimeMillis()
        val lastFetchTime = lastFetchTimeByCategory[category] ?: 0

        return if ((currentTime - lastFetchTime) < cacheDuration && cachedMealsByCategory.containsKey(category)) {
            cachedMealsByCategory[category]!!
        } else {
            val mealsFromApi = remoteRepository.getMealsFromCategory(category)
            cachedMealsByCategory[category] = mealsFromApi
            lastFetchTimeByCategory[category] = currentTime
            mealsFromApi
        }
    }

    override suspend fun getMealsFromCountry(country: String): List<MealUIModel> {
        return remoteRepository.getMealsFromCountry(country)
    }

    override suspend fun getMealDetails(idMeal: String): MealDetailUIModel {
        val currentTime = System.currentTimeMillis()
        val lastFetchTime = lastFetchTimeById[idMeal] ?: 0
        return if ((currentTime - lastFetchTime) < cacheDuration && cachedMealDetailById.containsKey(idMeal)) {
            cachedMealDetailById[idMeal]!!
        } else {
            val mealDetailsFromApi = remoteRepository.getMealDetails(idMeal)
            cachedMealDetailById[idMeal] = mealDetailsFromApi
            lastFetchTimeById[idMeal] = currentTime
            mealDetailsFromApi
        }
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealUIModel? {
        return localRepository.getFavoriteMealsById(idMeal)
    }

    override fun isCategoryCacheValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastFetchTime) < cacheDuration && cachedCategories.isNotEmpty()
    }

    override fun isMealCacheValid(category: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastFetchTime = lastFetchTimeByCategory[category] ?: 0
        return (currentTime - lastFetchTime) < cacheDuration && cachedMealsByCategory.containsKey(category)
    }

    override fun isMealDetailCacheValid(idMeal: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastFetchTime = lastFetchTimeById[idMeal] ?: 0
        return (currentTime - lastFetchTime) < cacheDuration && cachedMealDetailById.containsKey(idMeal)
    }

}