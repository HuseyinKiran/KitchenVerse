package com.huseyinkiran.kitchenapp.presentation.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.huseyinkiran.kitchenapp.databinding.CellCategoryBinding
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel

class CategoryAdapter(private val callback: CategoryListener) :
    ListAdapter<CategoryUIModel, CategoryViewHolder>(CategoryDiffCallback()) {

    interface CategoryListener {
        fun onCategoryClick(categoryName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category = category, callback = callback)
    }

    fun loadCategories(newList: List<CategoryUIModel>) {
        submitList(newList)
    }

}