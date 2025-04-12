package com.huseyinkiran.kitchenapp.presentation.meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.huseyinkiran.kitchenapp.databinding.FragmentMealBinding
import com.huseyinkiran.kitchenapp.domain.model.MealUIModel
import com.huseyinkiran.kitchenapp.presentation.adapter.meal.MealAdapter
import com.huseyinkiran.kitchenapp.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealFragment : Fragment() {

    private val viewModel: MealViewModel by viewModels()
    private lateinit var adapter: MealAdapter
    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = MealFragmentArgs.fromBundle(requireArguments()).category

        handleBackNavigation()
        setupRecyclerView()
        observeMeals()

        viewModel.getMealsFromCategory(category)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMealsFromCategory(category)
        }

    }

    private fun handleBackNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = MealAdapter(callback = object : MealAdapter.MealListener {
            override fun onMealClick(idMeal: String) {
                val action = MealFragmentDirections.actionMealFragmentToMealDetailFragment(idMeal)
                findNavController().navigate(action)
            }

            override fun onFavoriteClick(meal: MealUIModel) {
                viewModel.updateFavoriteState(meal)
            }

        })
        binding.rVMeal.itemAnimator = null
        binding.rVMeal.layoutManager = GridLayoutManager(context, 2)
        binding.rVMeal.adapter = adapter
    }

    private fun observeMeals() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealsFromCategory.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            txtError.isGone = true
                            swipeRefresh.isRefreshing = false
                            rVMeal.isGone = true
                        }

                        is Resource.Success -> {
                            progressBar.isGone = true
                            txtError.isGone = true
                            swipeRefresh.isRefreshing = false
                            resource.data?.let { adapter.loadMeals(it) }
                            rVMeal.isVisible = true
                        }

                        is Resource.Error -> {
                            progressBar.isGone = true
                            txtError.isVisible = true
                            swipeRefresh.isRefreshing = false
                            rVMeal.isGone = true
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}