package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.local.MealDao
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toEntity
import com.huseyinkiran.kitchenapp.domain.model.toUI
import com.huseyinkiran.kitchenapp.domain.repository.MealsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealsLocalRepositoryImpl @Inject constructor(
    private val dao: MealDao
) : MealsLocalRepository{

    override suspend fun upsertMeal(meal: MealUIModel) {
        dao.upsertMeal(meal.toEntity())
    }

    override suspend fun deleteMeal(meal: MealUIModel) {
        dao.deleteMeal(meal.toEntity())
    }

    override fun getFavoriteMeals(): Flow<List<MealUIModel>> {
        return dao.getFavoriteMeals().map {  mealEntityList ->
            mealEntityList.map { it.toUI() }
        }
    }

    override suspend fun getFavoriteMealsById(idMeal: String): MealUIModel? {
        return dao.getFavoriteMealsById(idMeal)?.toUI()
    }

}