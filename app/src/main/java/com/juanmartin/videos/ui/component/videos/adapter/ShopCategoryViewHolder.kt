package com.juanmartin.videos.ui.component.videos.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.videos.*
import com.juanmartin.videos.databinding.ShopCategoryItemBinding
import com.juanmartin.videos.ui.component.videos.listeners.RecyclerShowCategoryItemListener
import java.io.InputStream

class ShopCategoryViewHolder(private val itemBinding: ShopCategoryItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: String, recyclerItemListener: RecyclerShowCategoryItemListener) {
        drawCategoryImage(category)
        itemBinding.txtTitleCategory.text = category

        itemBinding.rlCategoryItem.setOnClickListener {
            recyclerItemListener.onItemSelected(
                category
            )
        }
    }

    private fun drawCategoryImage(category: String) {
        val resource: Bitmap
        var resourceColor = 0
        when (category) {
            SHOPPING_CATEGORY -> {
                resource = getResourceFromAssets("Cart_white.png")
                resourceColor =
                    ContextCompat.getColor(itemView.context, R.color.background_shopping);
            }
            FOOD_CATEGORY -> {
                resource = getResourceFromAssets("Catering_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_food);
            }
            LEISURE_CATEGORY -> {
                resource = getResourceFromAssets("Leisure_white.png")
                resourceColor =
                    ContextCompat.getColor(itemView.context, R.color.background_leisure);
            }
            BEAUTY_CATEGORY -> {
                resource = getResourceFromAssets("Truck_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_beauty);
            }
            else -> {
                //OTHER
                resource = getResourceFromAssets("EES_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_other);
            }
        }
        itemBinding.imgCategory.setImageBitmap(resource)
        itemBinding.imgCategory.setColorFilter(
            resourceColor,
            android.graphics.PorterDuff.Mode.MULTIPLY
        );
        itemBinding.txtTitleCategory.setTextColor(resourceColor)
    }

    fun getResourceFromAssets(fileName: String): Bitmap {
        val result: InputStream = itemView.context.assets.open(fileName)
        return BitmapFactory.decodeStream(result)
    }
}
