package com.huseyinkiran.kitchenapp.presentation.meal

import com.huseyinkiran.kitchenapp.MainDispatcherRule
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.data.api.FakeMealsApi
import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.data.repository.FakeMealsRepository
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class MealViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MealViewModel
    private lateinit var fakeRepository: FakeMealsRepository
    private lateinit var fakeApi: FakeMealsApi
    private lateinit var fakeDao: FakeMealDao

    @Before
    fun setup() {
        fakeApi = FakeMealsApi()
        fakeDao = FakeMealDao()
        fakeRepository = FakeMealsRepository(fakeApi, fakeDao)
        viewModel = MealViewModel(fakeRepository)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val state = viewModel.mealsFromCategory.value
        assertTrue(state is Resource.Loading)
    }

    @Test
    fun `getMealsFromCategories returns Success`() = runTest {
        val fakeMeal1 = MealUIModel("41","Hamburger","",false)
        val fakeMeal2 = MealUIModel("326","Lazagna","",false)

        fakeRepository.setMealsFromCategory(listOf(fakeMeal1, fakeMeal2))

        viewModel.getMealsFromCategory("Beef")
        advanceUntilIdle()

        val state = viewModel.mealsFromCategory.value
        assertTrue(state is Resource.Success)
        assertEquals(2, state.data?.size)
    }

    @Test
    fun `getMealsFromCategories returns Error`() = runTest {
        fakeRepository.setShouldThrowError(true)

        viewModel.getMealsFromCategory("Beef")
        advanceUntilIdle()

        val state = viewModel.mealsFromCategory.value
        assertTrue(state is Resource.Error)
    }

    @Test
    fun `add meals to favorites`() = runTest {
        val fakeMeal = MealUIModel("51","Hamburger","",false)
        fakeRepository.setMealsFromCategory(listOf(fakeMeal))

        viewModel.getMealsFromCategory("Beef")
        advanceUntilIdle()

        viewModel.updateFavoriteState(fakeMeal)
        advanceUntilIdle()

        val state = viewModel.mealsFromCategory.value

        assertTrue(state is Resource.Success)
        assertEquals(true, state.data!!.first().isFavorite)
    }

    @Test
    fun `remove meal from favorites`() = runTest {
        val fakeMeal = MealUIModel("17","Pizza","",false)
        fakeRepository.setMealsFromCategory(listOf(fakeMeal))
        fakeRepository.upsertMeal(fakeMeal)

        viewModel.getMealsFromCategory("Beef")
        advanceUntilIdle()

        viewModel.updateFavoriteState(fakeMeal)
        advanceUntilIdle()

        val state = viewModel.mealsFromCategory.value

        assertTrue(state is Resource.Success)
        assertEquals(false, state.data!!.first().isFavorite)
    }

}