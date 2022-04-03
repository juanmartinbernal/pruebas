package com.juanmartin.ui.base.listeners

import com.juanmartin.data.dto.comercios.ShopsItem

interface RecyclerItemListener {
    fun onItemSelected(shopItem: ShopsItem)
}