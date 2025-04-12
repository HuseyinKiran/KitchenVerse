package com.huseyinkiran.kitchenapp.presentation.adapter.category

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huseyinkiran.kitchenapp.databinding.CellCategoryBinding
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel

class CategoryViewHolder(private val binding: CellCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: CategoryUIModel, callback: CategoryAdapter.CategoryListener) =
        with(binding) {

            txtCategory.text = category.strCategory

            Glide.with(imgCategory.context)
                .load(category.strCategoryThumb)
                .into(imgCategory)

            cVCategory.setOnClickListener {
                callback.onCategoryClick(category.strCategory)
            }

        }

}
