package com.juanmartin.ui.component.shops.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.*
import com.juanmartin.R.color.*
import com.juanmartin.databinding.ShopCategoryItemBinding
import com.juanmartin.ui.component.shops.listeners.RecyclerShowCategoryItemListener
import java.io.InputStream

class ShopCategoryViewHolder(private val itemBinding: ShopCategoryItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: String, recyclerItemListener: RecyclerShowCategoryItemListener) {
        drawCategoryImage(category)
        itemBinding.txtTitleCategory.visibility = if (category.isEmpty())  View.GONE else  View.VISIBLE
        itemBinding.txtTitleCategory.text = category
        itemBinding.rlCategoryItem.setOnClickListener {
            recyclerItemListener.onItemSelected(
                category
            )
        }
    }

    private fun drawCategoryImage(category: String) {
        val resource: Bitmap
        val resourceColor : Int
        when (category) {
            SHOPPING_CATEGORY -> {
                resource = getResourceFromAssets("Cart_white.png")
                resourceColor =
                    ContextCompat.getColor(itemView.context, background_shopping)
            }
            FOOD_CATEGORY -> {
                resource = getResourceFromAssets("Catering_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, background_food)
            }
            LEISURE_CATEGORY -> {
                resource = getResourceFromAssets("Leisure_white.png")
                resourceColor =
                    ContextCompat.getColor(itemView.context, background_leisure)
            }
            BEAUTY_CATEGORY -> {
                resource = getResourceFromAssets("Truck_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, background_beauty)
            }
            ALL_CATEGORY -> {
                resource = getResourceFromAssets("placeholder.png")
                resourceColor = ContextCompat.getColor(itemView.context, background_beauty)
            }
            else -> {
                //OTHER
                resource = getResourceFromAssets("EES_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, background_other)
            }
        }
        itemBinding.imgCategory.setImageBitmap(resource)
        itemBinding.imgCategory.setColorFilter(
            resourceColor,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        itemBinding.txtTitleCategory.setTextColor(resourceColor)
    }

    private fun getResourceFromAssets(fileName: String): Bitmap {
        val result: InputStream = itemView.context.assets.open(fileName)
        return BitmapFactory.decodeStream(result)
    }
}
