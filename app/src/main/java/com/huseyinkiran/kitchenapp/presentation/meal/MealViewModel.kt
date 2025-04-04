package com.huseyinkiran.kitchenapp.presentation.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import com.huseyinkiran.kitchenapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealsRepository
) : ViewModel() {

    private val _mealsFromCategory =
        MutableStateFlow<Resource<List<MealUIModel>>>(Resource.Loading())
    val mealsFromCategory: StateFlow<Resource<List<MealUIModel>>> = _mealsFromCategory

    fun getMealsFromCategory(category: String) = viewModelScope.launch {
        _mealsFromCategory.value = Resource.Loading()
        try {
            val response = repository.getMealsFromCategory(category)
            val favorites = repository.getFavoriteMeals().first()
            val favoritesById = favorites.map { it.idMeal }
            val updatedMeals = response.map { meal ->
                meal.copy(isFavorite = favoritesById.contains(meal.idMeal))
            }
            _mealsFromCategory.value = Resource.Success(updatedMeals)
        } catch (e: Exception) {
            _mealsFromCategory.value = Resource.Error(e.localizedMessage ?: "Unknown Error !")
        }

    }

    private fun upsertMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.upsertMeal(meal)
    }

    private fun deleteMeal(meal: MealUIModel) = viewModelScope.launch {
        repository.deleteMeal(meal)
    }

    fun updateFavoriteState(meal: MealUIModel) = viewModelScope.launch {
        val isFav = repository.getFavoriteMealsById(meal.idMeal) != null
        if (isFav) {
            deleteMeal(meal)
        } else {
            upsertMeal(meal)
        }
        _mealsFromCategory.value.data?.let { currentList ->
            val updatedList = currentList.map {
                if (meal.idMeal == it.idMeal) it.copy(isFavorite = !isFav)
                else it
            }
            _mealsFromCategory.value = Resource.Success(updatedList)
        }
    }

}