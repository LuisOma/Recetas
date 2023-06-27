package com.example.recipes.ui.home

import android.os.Bundle
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import com.example.recipes.R
import com.example.recipes.base.BaseAdapter
import com.example.recipes.data.domain.Receta
import com.example.recipes.databinding.ItemRecipeBinding
import com.example.recipes.ui.util.Constants.Companion.RECIPE
import com.squareup.picasso.Picasso
import java.util.Locale

class RecipesAdapter : BaseAdapter<Receta, ItemRecipeBinding>(ItemRecipeBinding::inflate),
    Filterable {

    private var filteredList: List<Receta> = ArrayList()

    init {
        filteredList = itemList
    }


    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemRecipeBinding>,
        position: Int
    ) {
        val item = filteredList[position]

        holder.binding.recipeNameText.text = item.nombre
        val originalRepo = item.imagen
        val splitUrl = originalRepo.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val imageLink = "https://drive.google.com/uc?export=download&id=" + splitUrl[5]
        Picasso.with(holder.binding.recipeImage.context).load(imageLink).fit().centerCrop()
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .into(holder.binding.recipeImage)

        holder.binding.recipeQualificationText.text = item.calificacion.toString()
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, item, position)
            val bundle = Bundle().apply {
                putParcelable(RECIPE, item)
            }
            it.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = itemList
                } else {
                    val resultList = ArrayList<Receta>()
                    for (row in filteredList) {
                        if ((row.nombre.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))|| row.ingredientes.any { it.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) })
                        ) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Receta>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

}