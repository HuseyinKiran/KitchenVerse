package com.huseyinkiran.kitchenapp.presentation.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealsRepository
) : ViewModel() {

    private val _meal = MutableStateFlow<Resource<MealDetailUIModel?>>(Resource.Loading())
    val meal: StateFlow<Resource<MealDetailUIModel?>> = _meal

    fun getMealDetails(idMeal: String) = viewModelScope.launch {
        val isCacheValid = repository.isMealDetailCacheValid(idMeal)
        if (!isCacheValid) {
            _meal.value = Resource.Loading()
        }
        try {
            val response = repository.getMealDetails(idMeal)
            val isFav = repository.getFavoriteMealsById(idMeal) != null
            if (isCacheValid) {
                _meal.value = Resource.CacheSuccess(response.copy(isFavorite = isFav))
            } else {
                _meal.value = Resource.Success(response.copy(isFavorite = isFav))
            }
        } catch (e: Exception) {
            _meal.value = Resource.Error(e.localizedMessage ?: "Unknown Error !")
        }
    }

    private fun upsertMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.upsertMeal(meal)
    }

    private fun deleteMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.deleteMeal(meal)
    }

    fun updateFavoriteState() = viewModelScope.launch {
        val currentMeal = when(val resource = _meal.value) {
            is Resource.CacheSuccess -> resource.data
            is Resource.Success -> resource.data
            else -> null
        } ?: return@launch
        val isCacheValid = repository.isMealDetailCacheValid(currentMeal.idMeal)
        val isFav = repository.getFavoriteMealsById(currentMeal.idMeal) != null
        if (isFav) {
            deleteMeal(currentMeal.toMealUI())
        } else {
            upsertMeal(currentMeal.toMealUI())
        }
        if (isCacheValid) _meal.value = Resource.CacheSuccess(currentMeal.copy(isFavorite = !isFav))
        else _meal.value = Resource.Success(currentMeal.copy(isFavorite = !isFav))
    }

}