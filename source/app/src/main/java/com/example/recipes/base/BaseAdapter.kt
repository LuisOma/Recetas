package com.example.recipes.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdapter<T : Any, VB : ViewBinding>(private val inflate: Inflate<VB>) :
    RecyclerView.Adapter<BaseAdapter.Companion.BaseViewHolder<VB>>() {

    var emptyItems = MutableLiveData<Boolean>()

    var itemList = mutableListOf<T>()
    var listener: ((view: View, item: T, position: Int) -> Unit)? = null

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        inflate.invoke(LayoutInflater.from(parent.context), parent, false)
    )

    companion object {
        class BaseViewHolder<VB : ViewBinding>(val binding: VB) :
            RecyclerView.ViewHolder(binding.root)
    }

    fun addItems(items: List<T>) {
        itemList = if (items.isNotEmpty()) {
            items as MutableList<T>
        } else {
            mutableListOf()
        }
        notifyDataSetChanged()
        emptyItems.postValue(items.isEmpty())
    }
}