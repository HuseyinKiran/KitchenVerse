package com.huseyinkiran.kitchenapp.presentation.mealDetail

import com.huseyinkiran.kitchenapp.MainDispatcherRule
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.data.api.FakeMealsApi
import com.huseyinkiran.kitchenapp.data.local.FakeMealDao
import com.huseyinkiran.kitchenapp.data.repository.FakeMealsRepository
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.toMealUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class MealDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MealDetailViewModel
    private lateinit var fakeRepository: FakeMealsRepository
    private lateinit var fakeApi: FakeMealsApi
    private lateinit var fakeDao: FakeMealDao

    @Before
    fun setup() {
        fakeApi = FakeMealsApi()
        fakeDao = FakeMealDao()
        fakeRepository = FakeMealsRepository(fakeApi, fakeDao)
        viewModel = MealDetailViewModel(fakeRepository)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val state = viewModel.meal.value
        assertTrue(state is Resource.Loading)
    }

    @Test
    fun `getMealDetails is Success`() = runTest {
        val fakeMealDetail = MealDetailUIModel(
            idMeal = "1",
            strMeal = "Chicken Alfredo",
            strCategory = "Pasta",
            strArea = "Italian",
            strInstructions = "Cook pasta according to package instructions. In a separate pan, cook chicken and then mix with Alfredo sauce. Combine with pasta and serve hot.",
            strMealThumb = "https://example.com/images/chicken_alfredo.jpg",
            strTags = "Pasta,Main Course,Easy",
            strYoutube = "https://youtube.com/example1",
            strSource = "https://example.com/chicken-alfredo-recipe",
            isFavorite = false,
            strIngredient1 = "Chicken",
            strIngredient2 = "Pasta",
            strIngredient3 = "Alfredo Sauce",
            strIngredient4 = "Parmesan",
            strIngredient5 = "Garlic",
            strIngredient6 = "Butter",
            strIngredient7 = "Salt",
            strIngredient8 = "Pepper",
            strIngredient9 = "",
            strIngredient10 = "",
            strIngredient11 = "",
            strIngredient12 = "",
            strIngredient13 = "",
            strIngredient14 = "",
            strIngredient15 = "",
            strIngredient16 = "",
            strIngredient17 = "",
            strIngredient18 = "",
            strIngredient19 = "",
            strIngredient20 = "",
            strMeasure1 = "200g",
            strMeasure2 = "150g",
            strMeasure3 = "100g",
            strMeasure4 = "50g",
            strMeasure5 = "2 cloves",
            strMeasure6 = "2 tbsp",
            strMeasure7 = "to taste",
            strMeasure8 = "to taste",
            strMeasure9 = "",
            strMeasure10 = "",
            strMeasure11 = "",
            strMeasure12 = "",
            strMeasure13 = "",
            strMeasure14 = "",
            strMeasure15 = "",
            strMeasure16 = "",
            strMeasure17 = "",
            strMeasure18 = "",
            strMeasure19 = "",
            strMeasure20 = ""
        )

        fakeRepository.setMealDetails(listOf(fakeMealDetail))

        viewModel.getMealDetails("1")
        advanceUntilIdle()

        val state = viewModel.meal.value
        assertTrue(state is Resource.Success)
        assertEquals("1", state.data?.idMeal)
        assertEquals("Italian", state.data?.strArea)
    }

    @Test
    fun `getMealDetails is Error`() = runTest {
        fakeRepository.setShouldThrowError(true)

        viewModel.getMealDetails("532")
        advanceUntilIdle()

        val state = viewModel.meal.value
        assertTrue(state is Resource.Error)
    }

    @Test
    fun `add meals to favorites`() = runTest {
        val fakeMealDetail = MealDetailUIModel(
            idMeal = "1",
            strMeal = "Chicken Alfredo",
            strCategory = "Pasta",
            strArea = "Italian",
            strInstructions = "Cook pasta according to package instructions. In a separate pan, cook chicken and then mix with Alfredo sauce. Combine with pasta and serve hot.",
            strMealThumb = "https://example.com/images/chicken_alfredo.jpg",
            strTags = "Pasta,Main Course,Easy",
            strYoutube = "https://youtube.com/example1",
            strSource = "https://example.com/chicken-alfredo-recipe",
            isFavorite = false,
            strIngredient1 = "Chicken",
            strIngredient2 = "Pasta",
            strIngredient3 = "Alfredo Sauce",
            strIngredient4 = "Parmesan",
            strIngredient5 = "Garlic",
            strIngredient6 = "Butter",
            strIngredient7 = "Salt",
            strIngredient8 = "Pepper",
            strIngredient9 = "",
            strIngredient10 = "",
            strIngredient11 = "",
            strIngredient12 = "",
            strIngredient13 = "",
            strIngredient14 = "",
            strIngredient15 = "",
            strIngredient16 = "",
            strIngredient17 = "",
            strIngredient18 = "",
            strIngredient19 = "",
            strIngredient20 = "",
            strMeasure1 = "200g",
            strMeasure2 = "150g",
            strMeasure3 = "100g",
            strMeasure4 = "50g",
            strMeasure5 = "2 cloves",
            strMeasure6 = "2 tbsp",
            strMeasure7 = "to taste",
            strMeasure8 = "to taste",
            strMeasure9 = "",
            strMeasure10 = "",
            strMeasure11 = "",
            strMeasure12 = "",
            strMeasure13 = "",
            strMeasure14 = "",
            strMeasure15 = "",
            strMeasure16 = "",
            strMeasure17 = "",
            strMeasure18 = "",
            strMeasure19 = "",
            strMeasure20 = ""
        )
        fakeRepository.setMealDetails(listOf(fakeMealDetail))

        viewModel.getMealDetails(fakeMealDetail.idMeal)
        advanceUntilIdle()

        viewModel.updateFavoriteState()
        advanceUntilIdle()

        val state = viewModel.meal.value

        assertTrue(state is Resource.Success)
        assertEquals(true, state.data?.isFavorite)
    }

    @Test
    fun `remove meal from favorites`() = runTest {
        val fakeMealDetail = MealDetailUIModel(
            idMeal = "1",
            strMeal = "Chicken Alfredo",
            strCategory = "Pasta",
            strArea = "Italian",
            strInstructions = "Cook pasta according to package instructions. In a separate pan, cook chicken and then mix with Alfredo sauce. Combine with pasta and serve hot.",
            strMealThumb = "https://example.com/images/chicken_alfredo.jpg",
            strTags = "Pasta,Main Course,Easy",
            strYoutube = "https://youtube.com/example1",
            strSource = "https://example.com/chicken-alfredo-recipe",
            isFavorite = false,
            strIngredient1 = "Chicken",
            strIngredient2 = "Pasta",
            strIngredient3 = "Alfredo Sauce",
            strIngredient4 = "Parmesan",
            strIngredient5 = "Garlic",
            strIngredient6 = "Butter",
            strIngredient7 = "Salt",
            strIngredient8 = "Pepper",
            strIngredient9 = "",
            strIngredient10 = "",
            strIngredient11 = "",
            strIngredient12 = "",
            strIngredient13 = "",
            strIngredient14 = "",
            strIngredient15 = "",
            strIngredient16 = "",
            strIngredient17 = "",
            strIngredient18 = "",
            strIngredient19 = "",
            strIngredient20 = "",
            strMeasure1 = "200g",
            strMeasure2 = "150g",
            strMeasure3 = "100g",
            strMeasure4 = "50g",
            strMeasure5 = "2 cloves",
            strMeasure6 = "2 tbsp",
            strMeasure7 = "to taste",
            strMeasure8 = "to taste",
            strMeasure9 = "",
            strMeasure10 = "",
            strMeasure11 = "",
            strMeasure12 = "",
            strMeasure13 = "",
            strMeasure14 = "",
            strMeasure15 = "",
            strMeasure16 = "",
            strMeasure17 = "",
            strMeasure18 = "",
            strMeasure19 = "",
            strMeasure20 = ""
        )

        fakeRepository.setMealDetails(listOf(fakeMealDetail))
        fakeRepository.upsertMeal(fakeMealDetail.toMealUI())

        viewModel.getMealDetails(fakeMealDetail.idMeal)
        advanceUntilIdle()

        viewModel.updateFavoriteState()
        advanceUntilIdle()

        val state = viewModel.meal.value

        assertTrue(state is Resource.Success)
        assertEquals(false, state.data?.isFavorite)
    }

}