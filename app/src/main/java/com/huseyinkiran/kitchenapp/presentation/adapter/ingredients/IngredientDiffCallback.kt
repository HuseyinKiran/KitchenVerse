package com.huseyinkiran.kitchenapp.presentation.adapter.ingredients

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.kitchenapp.domain.model.IngredientUIModel

class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientUIModel>() {

    override fun areItemsTheSame(oldItem: IngredientUIModel, newItem: IngredientUIModel): Boolean {
        return oldItem.ingredient == newItem.ingredient
    }

    override fun areContentsTheSame(
        oldItem: IngredientUIModel,
        newItem: IngredientUIModel
    ): Boolean {
        return oldItem == newItem
    }
}