package com.huseyinkiran.kitchenapp.data.repository

import com.huseyinkiran.kitchenapp.data.api.FakeMealsApi
import com.huseyinkiran.kitchenapp.data.remote.model.CategoryDto
import com.huseyinkiran.kitchenapp.data.remote.model.CategoryResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealDetailDto
import com.huseyinkiran.kitchenapp.data.remote.model.MealDetailResponse
import com.huseyinkiran.kitchenapp.data.remote.model.MealDto
import com.huseyinkiran.kitchenapp.data.remote.model.MealResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class FakeMealsRemoteRepositoryTest {

    private lateinit var fakeApi: FakeMealsApi
    private lateinit var fakeRepository: FakeMealsRemoteRepository

    @Before
    fun setup() {
        fakeApi = FakeMealsApi()
        fakeRepository = FakeMealsRemoteRepository(fakeApi)
    }

    @Test
    fun `getCategories returns all Categories`() = runTest {
        val fakeCategory1 = CategoryDto("1", "Seafood", "", "")
        val fakeCategory2 = CategoryDto("2", "Chicken", "", "")
        fakeApi.setCategoryResponse(CategoryResponse(listOf(fakeCategory1, fakeCategory2)))

        val result = fakeRepository.getCategories()

        assertEquals(2, result.size)
        assertEquals("Seafood", result[0].strCategory)
        assertEquals("Chicken",result[1].strCategory)
    }

    @Test
    fun `get meals from its category`() = runTest {
        val fakeMeal1 = MealDto("21", "Chicken Roast", "")
        val fakeMeal2 = MealDto("55", "French Fries", "")
        fakeApi.setMealResponse(MealResponse(listOf(fakeMeal1, fakeMeal2)))

        val result = fakeRepository.getMealsFromCategory("Seafood")

        assertEquals(2, result.size)
        assertEquals("21", result[0].idMeal)
        assertEquals("55",result[1].idMeal)
    }

    @Test
    fun `get meals from its country`() = runTest {
        val fakeMeal1 = MealDto("231", "Chicken Roast", "")
        val fakeMeal2 = MealDto("525", "French Fries", "")
        val fakeMeal3 = MealDto("147", "Lasagne", "")
        fakeApi.setMealResponse(MealResponse(listOf(fakeMeal1, fakeMeal2, fakeMeal3)))

        val result = fakeRepository.getMealsFromCountry("Türkiye")

        assertEquals(3, result.size)
        assertEquals("231", result[0].idMeal)
        assertEquals("525", result[1].idMeal)
        assertEquals("147", result[2].idMeal)
    }

    @Test
    fun `get meal details`() = runTest {
        val fakeMealDetail = MealDetailDto(
            idMeal = "123",
            strMeal = "Pizza Margherita",
            strMealAlternate = null,
            strCategory = "Pizza",
            strArea = "Italian",
            strInstructions = "Bake it well.",
            strMealThumb = "https://example.com/pizza.jpg",
            strTags = "Cheese,Tomato",
            strYoutube = "https://youtube.com/fakevideo",
            strSource = null,
            strImageSource = null,
            strCreativeCommonsConfirmed = null,
            dateModified = null,
            strIngredient1 = "Cheese",
            strIngredient2 = "Tomato",
            strIngredient3 = "Basil",
            strIngredient4 = null,
            strIngredient5 = null,
            strIngredient6 = null,
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strMeasure1 = "100g",
            strMeasure2 = "2 pieces",
            strMeasure3 = "Few leaves",
            strMeasure4 = null,
            strMeasure5 = null,
            strMeasure6 = null,
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null
        )

        fakeApi.setMealDetailResponse(MealDetailResponse(listOf(fakeMealDetail)))

        val result = fakeRepository.getMealDetails(fakeMealDetail.idMeal!!)

        assertEquals(fakeMealDetail.idMeal, result.idMeal)
    }


}