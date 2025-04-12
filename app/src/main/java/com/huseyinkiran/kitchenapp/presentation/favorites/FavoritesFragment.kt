package com.huseyinkiran.kitchenapp.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.huseyinkiran.kitchenapp.databinding.FragmentFavoritesBinding
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.presentation.adapter.meal.MealAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var adapter: MealAdapter
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackNavigation()
        setupRecyclerView()
        observeFavoriteMeals()
    }

    private fun handleBackNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = MealAdapter(callback = object : MealAdapter.MealListener {
            override fun onMealClick(idMeal: String) {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailFragment(idMeal)
                findNavController().navigate(action)
            }

            override fun onFavoriteClick(meal: MealUIModel) {
                viewModel.deleteMeal(meal)
            }

        })
        binding.rVFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rVFavorites.adapter = adapter
    }

    private fun observeFavoriteMeals() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favoriteList ->
                    adapter.loadMeals(favoriteList.map { it.copy(isFavorite = true) })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}