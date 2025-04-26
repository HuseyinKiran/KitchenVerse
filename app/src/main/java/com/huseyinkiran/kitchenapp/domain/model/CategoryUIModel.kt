package com.huseyinkiran.kitchenapp.domain.model

import com.huseyinkiran.kitchenapp.data.remote.model.CategoryDto

data class CategoryUIModel(
    val strCategory: String,
    val strCategoryThumb: String
)

fun CategoryDto.toCategoryUI(): CategoryUIModel {
    return CategoryUIModel(
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb
    )
}

fun CategoryUIModel.toCategoryDto(): CategoryDto {
    return CategoryDto(
        idCategory = "",
        strCategory = strCategory,
        strCategoryDescription = "",
        strCategoryThumb = strCategoryThumb
    )
}