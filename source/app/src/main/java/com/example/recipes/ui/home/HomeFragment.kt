package com.example.recipes.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.base.BaseFragment
import com.example.recipes.base.showMessageError
import com.example.recipes.data.domain.Receta
import com.example.recipes.data.domain.core.ResponseState
import com.example.recipes.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel = HomeViewModel()

    private val recipesAdapter by lazy {
        RecipesAdapter()
    }

    override fun initComponents(savedInstanceState: Bundle?) {
        binding.recipesListRecycler.adapter = recipesAdapter
        viewModel.getRecipesList()

        binding.recipesListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.fabScroll.show()
                } else {
                    binding.fabScroll.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.fabScroll.setOnClickListener { view ->
            binding.recipesListRecycler.post {
                binding.recipesListRecycler.smoothScrollToPosition(0)
            }
        }

        binding.recipeSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                recipesAdapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun observe() {
        viewModel.getRecipesResponse.observe(this) {
            when (it) {
                is ResponseState.Loading -> {
                    Toast.makeText(requireContext(), getString(R.string.load_data), Toast.LENGTH_SHORT).show()
                }
                is ResponseState.Success -> {
                    (it.data as? List<Receta>)?.let { recipesModel ->
                        recipesAdapter.apply {
                            addItems(recipesModel)
                            filter.filter("")
                        }
                    }
                }
                is ResponseState.Error -> {
                    it.error.showMessageError(this)
                }
                else -> {getString(R.string.error)}
            }
        }
    }
}