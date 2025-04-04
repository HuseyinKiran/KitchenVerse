package com.huseyinkiran.kitchenapp.presentation.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.model.toMeal
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import com.huseyinkiran.kitchenapp.utils.Resource
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
        _meal.value = Resource.Loading()
        try {
            val response = repository.getMealDetails(idMeal)
            val isFav = repository.getFavoriteMealsById(idMeal) != null
            _meal.value = Resource.Success(response.copy(isFavorite = isFav))
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
        val currentMeal = (_meal.value as Resource.Success).data ?: return@launch
        val isFav = repository.getFavoriteMealsById(currentMeal.idMeal) != null
        if (isFav) {
            deleteMeal(currentMeal.toMeal())
        } else {
            upsertMeal(currentMeal.toMeal())
        }
        _meal.value = Resource.Success(currentMeal.copy(isFavorite = !isFav))
    }

}