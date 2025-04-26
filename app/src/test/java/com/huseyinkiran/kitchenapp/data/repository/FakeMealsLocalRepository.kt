package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toEntity
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import com.huseyinkiran.kitchenapp.domain.repository.MealsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeMealsLocalRepository(private val dao: FakeMealDao) : MealsLocalRepository {

    override suspend fun upsertMeal(meal: MealUIModel) {
        dao.upsertMeal(meal.toEntity())
    }

    override suspend fun deleteMeal(meal: MealUIModel) {
        dao.deleteMeal(meal.toEntity())
    }

    override fun getFavoriteMeals(): Flow<List<MealUIModel>> {
        return dao.getFavoriteMeals().map { mealEntityList ->
            mealEntityList.map { mealEntity ->
                mealEntity.toMealUI()
            }
        }
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealUIModel? {
        return dao.getFavoriteMealsById(idMeal)?.toMealUI()
    }
}