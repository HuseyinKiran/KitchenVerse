package com.huseyinkiran.kitchenapp.presentation.adapter.ingredients

import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.kitchenapp.databinding.CellIngredientBinding
import com.huseyinkiran.kitchenapp.domain.model.IngredientUIModel

class IngredientViewHolder(private val binding: CellIngredientBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: IngredientUIModel) = with(binding) {
        txtIngredient.text = ingredient.ingredient
        txtMeasure.text = ingredient.measure
    }

}