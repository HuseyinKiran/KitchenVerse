package com.huseyinkiran.kitchenapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel

class MealDiffCallback : DiffUtil.ItemCallback<MealUIModel>() {
    override fun areItemsTheSame(oldItem: MealUIModel, newItem: MealUIModel): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealUIModel, newItem: MealUIModel): Boolean {
        return oldItem == newItem
    }
}