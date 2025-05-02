package com.huseyinkiran.kitchenapp.presentation.adapter.meal

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huseyinkiran.kitchenapp.R
import com.huseyinkiran.kitchenapp.databinding.CellMealBinding
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel

class MealViewHolder(private val binding: CellMealBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(meal: MealUIModel, callback: MealAdapter.MealListener) = with(binding) {

        txtMealName.text = meal.strMeal

        Glide.with(itemView.context)
            .load(meal.strMealThumb)
            .into(imgMeal)

        cvMeal.setOnClickListener {
            callback.onMealClick(meal.idMeal)
        }

        btnFav.setImageResource(
            if (meal.isFavorite) R.drawable.btn_remove_fav_small
            else R.drawable.btn_add_fav_small
        )

        btnFav.setOnClickListener {
            callback.onFavoriteClick(meal)
        }
    }

}
