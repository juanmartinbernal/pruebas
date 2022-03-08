package com.juanmartin.videos.ui.base.listeners

import com.juanmartin.videos.data.dto.videos.CategoryItem

interface RecyclerItemListener {
    fun onItemSelected(categoryItem: CategoryItem)
}