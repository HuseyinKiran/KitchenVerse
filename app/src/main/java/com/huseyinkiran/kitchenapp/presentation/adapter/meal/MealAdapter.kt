package com.huseyinkiran.kitchenapp.presentation.adapter.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.huseyinkiran.kitchenapp.databinding.CellMealBinding
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel

class MealAdapter(
    private val callback: MealListener
) : ListAdapter<MealUIModel, MealViewHolder>(MealDiffCallback()) {

    interface MealListener {
        fun onMealClick(idMeal: String)
        fun onFavoriteClick(meal: MealUIModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = CellMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal = meal, callback = callback)
    }

    fun loadMeals(newList: List<MealUIModel>) {
        submitList(newList)
    }

}