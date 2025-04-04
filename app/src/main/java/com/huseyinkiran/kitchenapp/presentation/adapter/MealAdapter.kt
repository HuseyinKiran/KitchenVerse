package com.huseyinkiran.kitchenapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.huseyinkiran.kitchenapp.R
import com.huseyinkiran.kitchenapp.databinding.CellMealBinding
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel

class MealAdapter(
    private val onMealClick: (String) -> Unit,
    private val onFavoriteClick: (MealUIModel) -> Unit
) : ListAdapter<MealUIModel, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    class MealViewHolder(val binding: CellMealBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = CellMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)

        holder.binding.apply {
            txtMealName.text = meal.strMeal

            Glide.with(holder.itemView.context)
                .load(meal.strMealThumb)
                .into(imgMeal)

            cVMeal.setOnClickListener {
                onMealClick(meal.idMeal)
            }

            btnFav.setImageResource(
                if (meal.isFavorite) R.drawable.btn_remove_fav_small
                else R.drawable.btn_add_fav_small
            )

            btnFav.setOnClickListener {
                onFavoriteClick(meal)
            }
        }
    }

    fun loadMeals(newList: List<MealUIModel>) {
        submitList(newList)
    }

}