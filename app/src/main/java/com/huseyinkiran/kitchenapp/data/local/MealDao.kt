package com.huseyinkiran.kitchenapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Upsert
    suspend fun upsertMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM favoriteMeals")
    fun getFavoriteMeals(): Flow<List<MealEntity>>

    @Query("SELECT * FROM favoriteMeals WHERE idMeal = :idMeal")
    suspend fun getFavoriteMealsById(idMeal: String): MealEntity?

}