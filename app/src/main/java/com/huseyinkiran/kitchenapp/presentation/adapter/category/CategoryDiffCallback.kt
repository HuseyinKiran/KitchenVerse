package com.huseyinkiran.kitchenapp.presentation.adapter.category

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel

class CategoryDiffCallback: DiffUtil.ItemCallback<CategoryUIModel>() {
    override fun areItemsTheSame(oldItem: CategoryUIModel, newItem: CategoryUIModel): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }

    override fun areContentsTheSame(oldItem: CategoryUIModel, newItem: CategoryUIModel): Boolean {
        return oldItem == newItem
    }
}