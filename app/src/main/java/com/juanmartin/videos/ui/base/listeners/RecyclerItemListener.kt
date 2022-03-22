package com.juanmartin.videos.ui.base.listeners

import com.juanmartin.videos.data.dto.comercios.ShopsItem

interface RecyclerItemListener {
    fun onItemSelected(shopItem: ShopsItem)
}