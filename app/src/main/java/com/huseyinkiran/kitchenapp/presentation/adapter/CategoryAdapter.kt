package com.huseyinkiran.kitchenapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.huseyinkiran.kitchenapp.databinding.CellCategoryBinding
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : ListAdapter<CategoryUIModel, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    class CategoryViewHolder(val binding: CellCategoryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)

        holder.binding.apply {
            txtCategory.text = category.strCategory

            Glide.with(imgCategory.context)
                .load(category.strCategoryThumb)
                .into(imgCategory)

            cVCategory.setOnClickListener {
                onCategoryClick(category.strCategory)
            }
        }
    }

    fun loadCategories(newList: List<CategoryUIModel>) {
        submitList(newList)
    }

}