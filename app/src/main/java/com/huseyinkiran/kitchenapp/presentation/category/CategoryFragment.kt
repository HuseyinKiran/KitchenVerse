package com.huseyinkiran.kitchenapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.databinding.FragmentCategoryBinding
import com.huseyinkiran.kitchenapp.domain.model.CategoryUIModel
import com.huseyinkiran.kitchenapp.presentation.adapter.category.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var adapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToFavorites()
        setupRecyclerView()
        observeCategories()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadCategories()
        }

    }

    private fun navigateToFavorites() {
        binding.btnGoToFavorite.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToFavoritesFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        adapter = CategoryAdapter(callback = object : CategoryAdapter.CategoryListener {
            override fun onCategoryClick(categoryName: String) {
                val action =
                    CategoryFragmentDirections.actionCategoryFragmentToMealFragment(categoryName)
                findNavController().navigate(action)
            }
        })
        binding.rVCategory.layoutManager = GridLayoutManager(context, 2)
        binding.rVCategory.adapter = adapter
    }

    private fun observeCategories() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { resource ->
                    manageCategoryResource(resource)
                }
            }
        }
        viewModel.loadCategories()
    }

    private fun FragmentCategoryBinding.manageCategoryResource(resource: Resource<List<CategoryUIModel>>) {
        when (resource) {
            is Resource.Loading -> {
                progressBar.isVisible = true
                txtError.isGone = true
                swipeRefresh.isRefreshing = false
                rVCategory.isGone = true
            }

            is Resource.Success -> {
                progressBar.isGone = true
                txtError.isGone = true
                rVCategory.isVisible = true
                swipeRefresh.isRefreshing = false
                resource.data?.let { adapter.loadCategories(it) }
            }

            is Resource.CacheSuccess -> {
                progressBar.isGone = true
                txtError.isGone = true
                rVCategory.isVisible = true
                swipeRefresh.isRefreshing = false
                resource.data?.let { adapter.loadCategories(it) }
            }

            is Resource.Error -> {
                progressBar.isGone = true
                txtError.isVisible = true
                rVCategory.isGone = true
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}