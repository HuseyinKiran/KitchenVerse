package com.huseyinkiran.kitchenapp.domain.model

import com.huseyinkiran.kitchenapp.data.remote.model.CategoryDto

data class CategoryUIModel(
    val strCategory: String,
    val strCategoryThumb: String
)

fun CategoryDto.toUI(): CategoryUIModel {
    return CategoryUIModel(
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb
    )
}