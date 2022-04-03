package com.juanmartin.ui.component.shops.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.databinding.ShopItemBinding
import com.juanmartin.ui.base.listeners.RecyclerItemListener
import com.juanmartin.ui.component.shops.ShopsListViewModel
import kotlin.collections.ArrayList


class ShopsAdapter(
    private val shopsListViewModel: ShopsListViewModel,
    private val shops: ArrayList<ShopsItem>
) : RecyclerView.Adapter<ShopsViewHolder>(), Filterable {

    var shopFilterList = ArrayList<ShopsItem>()

    init {
        shopFilterList = shops
    }

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(shopItem: ShopsItem) {
            shopsListViewModel.openShopDetails(shopItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsViewHolder {
        val itemBinding =
            ShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        holder.bind(shopFilterList[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return shopFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    shopFilterList = shops
                } else {
                    val resultList = ArrayList<ShopsItem>()
                    for (row in shops) {
                        if (row.category!= null && row.category.contains(charSearch) ||  row.name!!.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    shopFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = shopFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                shopFilterList = results?.values as ArrayList<ShopsItem>
                shopsListViewModel.updateShops(shopFilterList.size)
                notifyDataSetChanged()
            }

        }
    }

}

