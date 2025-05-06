package com.huseyinkiran.kitchenapp.presentation.mealDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.huseyinkiran.kitchenapp.R
import com.huseyinkiran.kitchenapp.databinding.FragmentMealDetailBinding
import com.huseyinkiran.kitchenapp.common.Resource
import com.huseyinkiran.kitchenapp.domain.model.MealDetailUIModel
import com.huseyinkiran.kitchenapp.domain.model.toIngredientList
import com.huseyinkiran.kitchenapp.presentation.adapter.ingredients.IngredientAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailFragment : Fragment() {

    private val viewModel: MealDetailViewModel by viewModels()
    private var _binding: FragmentMealDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var idMeal: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredientAdapter = IngredientAdapter()

        binding.rvIngredient.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = ingredientAdapter
        }

        idMeal = MealDetailFragmentArgs.fromBundle(requireArguments()).idMeal

        handleBackNavigation()
        observeMealDetails()
    }

    private fun handleBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeMealDetails() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.meal.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            txtError.isGone = true
                            svMain.isGone = true
                        }

                        is Resource.Success -> {
                            progressBar.isGone = true
                            txtError.isGone = true
                            svMain.isVisible = true

                            loadMealDetail(resource)
                        }

                        is Resource.CacheSuccess -> {
                            progressBar.isGone = true
                            txtError.isGone = true
                            svMain.isVisible = true

                            loadMealDetail(resource)
                        }

                        is Resource.Error -> {
                            progressBar.isGone = true
                            txtError.isVisible = true
                            svMain.isGone = true
                        }
                    }
                }
            }
        }
        viewModel.getMealDetails(idMeal)
    }

    private fun FragmentMealDetailBinding.loadMealDetail(resource: Resource<MealDetailUIModel?>) {
        resource.data?.let { meal ->
            txtMealName.text = meal.strMeal

            context?.let {
                Glide.with(it).load(meal.strMealThumb).into(imgMeal)
            }

            txtArea.text = meal.strArea
            txtCategory.text = meal.strCategory
            txtTags.text = meal.strTags
            txtInstruction.text = meal.strInstructions

            btnFav.setImageResource(
                if (meal.isFavorite) R.drawable.btn_remove_fav
                else R.drawable.btn_add_fav
            )

            btnFav.setOnClickListener {
                viewModel.updateFavoriteState()
            }

            ingredientAdapter.submitList(meal.toIngredientList())

            imgYoutube.setOnClickListener {
                val url = meal.strYoutube
                if (url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } else {
                    Snackbar.make(
                        it,
                        "Couldn't find the video connection",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}