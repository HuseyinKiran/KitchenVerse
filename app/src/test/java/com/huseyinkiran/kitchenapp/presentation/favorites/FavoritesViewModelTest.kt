package com.huseyinkiran.kitchenapp.presentation.favorites

import com.huseyinkiran.kitchenapp.MainDispatcherRule
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
class FavoritesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var fakeRepository: FakeMealsRepository
    private lateinit var fakeApi: FakeMealsApi
    private lateinit var fakeDao: FakeMealDao

    @Before
    fun setup() {
        fakeApi = FakeMealsApi()
        fakeDao = FakeMealDao()
        fakeRepository = FakeMealsRepository(fakeApi, fakeDao)
        viewModel = FavoritesViewModel(fakeRepository)
    }

    @Test
    fun `collect favorites and delete favorite meal from Favorites db`() = runTest {
        val fakeMeal1 = MealUIModel("23","Chicken Roast","",false)
        val fakeMeal2 = MealUIModel("231","French Fries","",false)

        fakeRepository.upsertMeal(fakeMeal1)
        fakeRepository.upsertMeal(fakeMeal2)
        advanceUntilIdle()

        val initialFavorites = viewModel.favorites.value
        assertEquals(2, initialFavorites.size)
        assertTrue(initialFavorites.contains(fakeMeal1))
        assertTrue(initialFavorites.contains(fakeMeal2))

        viewModel.deleteMeal(fakeMeal2)
        advanceUntilIdle()

        val finalFavorites = viewModel.favorites.value
        assertEquals(1, finalFavorites.size)
        assertTrue(finalFavorites.contains(fakeMeal1))
        assertFalse(finalFavorites.contains(fakeMeal2))
    }

}