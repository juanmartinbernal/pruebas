package com.juanmartin.videos.ui.component.videos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.videos.data.dto.videos.CategoryItem
import com.juanmartin.videos.databinding.CategoryItemBinding
import com.juanmartin.videos.ui.base.listeners.RecyclerItemListener
import com.juanmartin.videos.ui.component.videos.VideosCategoryListViewModel


class VideoCategoryAdapter(
    private val videosCategoryListViewModel: VideosCategoryListViewModel,
    private val categories: List<CategoryItem>
) : RecyclerView.Adapter<VideoCategoryViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(categoryItem: CategoryItem) {
            videosCategoryListViewModel.openCategoryDetails(categoryItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoCategoryViewHolder {
        val itemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoCategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VideoCategoryViewHolder, position: Int) {
        holder.bind(categories[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}

