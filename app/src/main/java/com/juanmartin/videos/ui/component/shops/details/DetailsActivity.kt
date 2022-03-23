package com.juanmartin.videos.ui.component.shops.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.juanmartin.videos.R
import com.juanmartin.videos.SHOP_ITEM_KEY
import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.comercios.ShopsItem
import com.juanmartin.videos.databinding.DetailsLayoutBinding
import com.juanmartin.videos.ui.base.BaseActivity
import com.juanmartin.videos.utils.observe
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: DetailsLayoutBinding
    private var menu: Menu? = null


    override fun initViewBinding() {
        binding = DetailsLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initIntentData(intent.getParcelableExtra(SHOP_ITEM_KEY) ?: ShopsItem())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.menu = menu

        return true
    }


    override fun observeViewModel() {
        observe(viewModel.shopData, ::initializeView)
    }


    private fun initializeView(shopItem: ShopsItem) {
        binding.tvName.text = shopItem.name
        binding.tvHeadline.text = shopItem.openingHours
        binding.tvDescription.text = shopItem.description
        Picasso.get().load(shopItem.logo?.url).placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivRecipeImage)

    }
}
