package com.huseyinkiran.kitchenapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.databinding.CellCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: CellCategoryBinding) : ViewHolder(binding.root)

    private var categoryList: List<CategoryUIModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val category = categoryList[position]

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

    @SuppressLint("NotifyDataSetChanged")
    fun loadCategories(list: List<CategoryUIModel>) {
        categoryList = list
        notifyDataSetChanged()
    }

}