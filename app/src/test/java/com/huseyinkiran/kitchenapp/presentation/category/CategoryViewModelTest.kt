package com.huseyinkiran.kitchenapp.presentation.category

import com.huseyinkiran.kitchenapp.MainDispatcherRule
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.data.api.FakeMealsApi
import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.data.repository.FakeMealsRepository
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class CategoryViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CategoryViewModel
    private lateinit var fakeRepository: FakeMealsRepository
    private lateinit var fakeApi: FakeMealsApi
    private lateinit var fakeDao: FakeMealDao

    @Before
    fun setup() {
        fakeApi = FakeMealsApi()
        fakeDao = FakeMealDao()
        fakeRepository = FakeMealsRepository(fakeApi, fakeDao)
        viewModel = CategoryViewModel(fakeRepository)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val state = viewModel.categories.value
        assertTrue(state is Resource.Loading)
    }

    @Test
    fun `loadCategories returns Success`() = runTest {
        val fakeCategory1 = CategoryUIModel("Lamb","")
        val fakeCategory2 = CategoryUIModel("Chicken","")

        fakeRepository.setCategories(listOf(fakeCategory1, fakeCategory2))

        viewModel.loadCategories()
        advanceUntilIdle()

        val state = viewModel.categories.value
        assertTrue(state is Resource.Success)
        assertEquals(2, viewModel.categories.value.data?.size)
    }

    @Test
    fun `loadCategories returns Error`() = runTest {
        fakeRepository.setShouldThrowError(true)

        val viewModel = CategoryViewModel(fakeRepository)
        advanceUntilIdle()

        val state = viewModel.categories.value
        assertTrue(state is Resource.Error)
    }

}