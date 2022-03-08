package com.juanmartin.videos.ui.component.videos.adapter

import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.videos.data.dto.videos.CategoryItem
import com.juanmartin.videos.databinding.CategoryItemBinding
import com.juanmartin.videos.ui.base.listeners.RecyclerItemListener


class VideoCategoryViewHolder(private val itemBinding: CategoryItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(categoryItem: CategoryItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvName.text = categoryItem.title
        itemBinding.tvCaption.text = categoryItem.type
        itemBinding.rlCategoryItem.setOnClickListener {
            recyclerItemListener.onItemSelected(
                categoryItem
            )
        }
    }
}

