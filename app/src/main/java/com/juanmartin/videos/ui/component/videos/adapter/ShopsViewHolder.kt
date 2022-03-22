package com.juanmartin.videos.ui.component.videos.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juanmartin.videos.*
import com.juanmartin.videos.data.dto.comercios.ShopsItem
import com.juanmartin.videos.databinding.ShopItemBinding
import com.juanmartin.videos.ui.base.listeners.RecyclerItemListener
import com.squareup.picasso.Picasso
import java.io.InputStream


class ShopsViewHolder(private val itemBinding: ShopItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(shopItem: ShopsItem, recyclerItemListener: RecyclerItemListener) {
        drawCategoryImage(shopItem.category)
        itemBinding.tvName.text = shopItem.name
        itemBinding.tvCaption.text = shopItem.openingHours
        itemBinding.tvCaption.isSelected = true
        if(shopItem.shortDescription == null) {
            itemBinding.tvDescrition.visibility = View.GONE
        }else{
            itemBinding.tvDescrition.visibility = View.VISIBLE
        }
        itemBinding.tvDescrition.text = shopItem.shortDescription
       // itemBinding.tvLocation.text = shopItem.address.country.plus(" ").plus( shopItem.address.city).plus(" ").plus(shopItem.address.street)
        Picasso.get().load(shopItem.logo?.url).placeholder(R.mipmap.ic_launcher)
            .into(itemBinding.ivShopItemImage)

        itemBinding.rlShopItem.setOnClickListener {
            recyclerItemListener.onItemSelected(
                shopItem
            )
        }
    }

    private fun drawCategoryImage(category : String?){
        var resource : Bitmap
        var resourceColor = 0
        when (category) {
            SHOPPING_CATEGORY -> {
                resource = getResourceFromAssets("Cart_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_shopping);
            }
            FOOD_CATEGORY -> {
                resource = getResourceFromAssets("Catering_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_food);
            }
            LEISURE_CATEGORY -> {
                resource = getResourceFromAssets("Leisure_white.png")
                resourceColor = ContextCompat.getColor(itemView.context, R.color.background_leisure);
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
        itemBinding.lnyHeader.setBackgroundColor(resourceColor)
    }

    fun getResourceFromAssets(fileName : String): Bitmap {
        val result : InputStream = itemView.context.assets.open(fileName)
        return BitmapFactory.decodeStream(result)
    }
}

