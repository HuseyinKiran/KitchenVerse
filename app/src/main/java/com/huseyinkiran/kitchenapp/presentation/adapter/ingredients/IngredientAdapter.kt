package com.huseyinkiran.kitchenapp.presentation.adapter.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.huseyinkiran.kitchenapp.databinding.CellIngredientBinding
import com.huseyinkiran.kitchenapp.domain.model.IngredientUIModel

class IngredientAdapter : ListAdapter<IngredientUIModel, IngredientViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = CellIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
    }

}