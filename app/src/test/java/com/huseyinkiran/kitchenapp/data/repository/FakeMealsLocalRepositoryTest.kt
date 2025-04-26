package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class FakeMealsLocalRepositoryTest {

    private lateinit var fakeDao: FakeMealDao
    private lateinit var fakeRepository: FakeMealsLocalRepository

    @Before
    fun setup() {
        fakeDao = FakeMealDao()
        fakeRepository = FakeMealsLocalRepository(fakeDao)
    }

    @Test
    fun `upsert to Favorites db`() = runTest {
        val meal = MealUIModel("33", "Hamburger", "", false)

        fakeRepository.upsertMeal(meal)

        val favorites = fakeRepository.getFavoriteMeals().first()
        assertTrue(favorites.contains(meal))
    }

    @Test
    fun `delete from Favorites db`() = runTest {
        val meal = MealUIModel("24", "Chicken Roast", "", false)

        fakeRepository.upsertMeal(meal)
        fakeRepository.deleteMeal(meal)

        val favorites = fakeRepository.getFavoriteMeals().first()
        assertFalse(favorites.contains(meal))
    }

    @Test
    fun `get all favorites from Favorites db`() = runTest {
        val meal1 = MealUIModel("1", "Food 1", "", false)
        val meal2 = MealUIModel("2", "Food 2", "", false)
        val meal3 = MealUIModel("3", "Food 3", "", false)

        fakeRepository.upsertMeal(meal1)
        fakeRepository.upsertMeal(meal2)
        fakeRepository.upsertMeal(meal3)

        val favorites = fakeRepository.getFavoriteMeals().first()
        assertEquals(3, favorites.size)
    }

    @Test
    fun `get favorite meal by meal id`() = runTest {
        val meal1 = MealUIModel("1", "Food 1", "", false)

        fakeRepository.upsertMeal(meal1)

        val favorite = fakeRepository.getFavoriteMealsById(meal1.idMeal)
        assertEquals(meal1.idMeal, favorite?.idMeal)
    }

}