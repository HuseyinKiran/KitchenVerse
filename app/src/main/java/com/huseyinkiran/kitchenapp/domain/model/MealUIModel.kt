package com.huseyinkiran.kitchenapp.domain.model

import com.huseyinkiran.kitchenapp.data.local.MealEntity
import com.huseyinkiran.kitchenapp.data.remote.model.MealDto

data class MealUIModel(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val isFavorite: Boolean
)

fun MealDto.toUI(): MealUIModel {
    return MealUIModel(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        isFavorite = false
    )
}

fun MealUIModel.toEntity(): MealEntity {
    return MealEntity(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun MealEntity.toUI(): MealUIModel {
    return MealUIModel(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        isFavorite = false
    )
}