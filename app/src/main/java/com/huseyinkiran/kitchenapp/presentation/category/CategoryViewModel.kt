package com.huseyinkiran.kitchenapp.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: MealsRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<Resource<List<CategoryUIModel>>>(Resource.Loading())
    val categories: StateFlow<Resource<List<CategoryUIModel>>> = _categories

    fun loadCategories() = viewModelScope.launch {
        val isCacheValid = repository.isCategoryCacheValid()
        if (!isCacheValid) _categories.value = Resource.Loading()
        try {
            val response = repository.getCategories()
            if (isCacheValid) {
                _categories.value = Resource.CacheSuccess(response)
            } else {
                _categories.value = Resource.Success(response)
            }
        } catch (e: Exception) {
            _categories.value = Resource.Error(e.localizedMessage ?: "Unknown Error !")
        }
    }

}