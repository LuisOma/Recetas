package com.example.recipes.ui.detail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.recipes.R
import com.example.recipes.base.BaseFragment
import com.example.recipes.data.domain.Receta
import com.example.recipes.databinding.FragmentDetailBinding
import com.example.recipes.ui.util.Constants.Companion.LAT
import com.example.recipes.ui.util.Constants.Companion.LON
import com.example.recipes.ui.util.Constants.Companion.RECIPE
import com.squareup.picasso.Picasso

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    var recipe: Receta? = null

    override fun initComponents(savedInstanceState: Bundle?) {
        arguments?.let {
            recipe = it.getParcelable(RECIPE)
        }

        val originalRepo = recipe?.imagen
        val splitUrl = originalRepo?.split("/".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val imageLink = "https://drive.google.com/uc?export=download&id=" + (splitUrl?.get(5) ?: 0)
        Picasso.with(requireContext()).load(imageLink).fit().centerCrop()
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .into(binding.recipeImage)
        binding.recipeNameText.text = recipe?.nombre
        binding.difficultyText.text = recipe?.dificultad
        binding.timeText.text = recipe?.tiempo.toString() + " min."
        binding.portionText.text = recipe?.porcion
        binding.descriptionValueText.text = recipe?.descripcion
        val ingredientesFormateados = recipe?.ingredientes?.joinToString("\n")
        binding.ingredientsValueText.text = ingredientesFormateados

        binding.originButton.setOnClickListener {
            val bundle = Bundle().apply {
                recipe?.lat?.let { it1 -> putDouble(LAT, it1) }
                recipe?.lon?.let { it1 -> putDouble(LON, it1) }
            }
            findNavController().navigate(R.id.action_detailFragment_to_mapFragment, bundle)
        }
    }
}
