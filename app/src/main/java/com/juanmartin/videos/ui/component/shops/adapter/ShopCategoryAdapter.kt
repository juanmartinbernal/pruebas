package com.juanmartin.videos.ui.component.shops.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.videos.databinding.ShopCategoryItemBinding
import com.juanmartin.videos.ui.component.shops.ShopsListViewModel
import com.juanmartin.videos.ui.component.shops.listeners.RecyclerShowCategoryItemListener

class ShopCategoryAdapter(
    private val shopsListViewModel: ShopsListViewModel,
    private val shopsCategories: List<String>
) : RecyclerView.Adapter<ShopCategoryViewHolder>() {

    private val onItemClickListener: RecyclerShowCategoryItemListener =
        object : RecyclerShowCategoryItemListener {
            override fun onItemSelected(category: String) {
                shopsListViewModel.filterByCategories(category);
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCategoryViewHolder {
        val itemBinding =
            ShopCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopCategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ShopCategoryViewHolder, position: Int) {
        holder.bind(shopsCategories[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return shopsCategories.size
    }
}