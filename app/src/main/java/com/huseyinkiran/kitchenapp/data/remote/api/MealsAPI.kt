package com.huseyinkiran.kitchenapp.data.remote.api

import com.huseyinkiran.kitchenapp.data.remote.model.CategoryResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealDetailResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsAPI {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsFromCategory(@Query("c") category: String): MealResponse

    @GET("filter.php")
    suspend fun getMealsFromCountry(@Query("a") country: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") idMeal: String): MealDetailResponse

}

