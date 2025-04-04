package com.huseyinkiran.kitchenapp.presentation.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toMeal
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealsRepository
) : ViewModel() {

    private val _meal = MutableStateFlow<MealDetailUIModel?>(null)
    val meal: StateFlow<MealDetailUIModel?> = _meal

    fun getMealDetails(idMeal: String) = viewModelScope.launch {
        try {
            val response = repository.getMealDetails(idMeal)
            val isFav = repository.getFavoriteMealsById(idMeal) != null
            _meal.value = response.copy(isFavorite = isFav)
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    private fun upsertMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.upsertMeal(meal)
    }

    private fun deleteMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.deleteMeal(meal)
    }

    fun updateFavoriteState() = viewModelScope.launch {
        _meal.value?.let { currentMeal ->
            val isFav = repository.getFavoriteMealsById(currentMeal.idMeal) != null
            if (isFav) {
                deleteMeal(currentMeal.toMeal())
            } else {
                upsertMeal(currentMeal.toMeal())
            }
            _meal.value = currentMeal.copy(isFavorite = !isFav)
        }
    }

}