package com.huseyinkiran.kitchenapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMeals")
data class MealEntity(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)