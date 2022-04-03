package com.juanmartin.ui.component.shops

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.juanmartin.ALL_CATEGORY
import com.juanmartin.R
import com.juanmartin.SHOP_ITEM_KEY
import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.data.error.SEARCH_ERROR
import com.juanmartin.databinding.ShopsActivityBinding
import com.juanmartin.ui.base.BaseActivity
import com.juanmartin.ui.component.shops.adapter.ShopCategoryAdapter
import com.juanmartin.ui.component.shops.adapter.ShopsAdapter
import com.juanmartin.ui.component.shops.details.DetailsActivity
import com.juanmartin.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopsListActivity : BaseActivity() {
    private lateinit var binding: ShopsActivityBinding

    private val shopsListViewModel: ShopsListViewModel by viewModels()
    private lateinit var shopAdapter: ShopsAdapter
    private lateinit var shopCategoryAdapter: ShopCategoryAdapter


    override fun initViewBinding() {
        binding = ShopsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.shops_title)
        val layoutManagerCategoriesShop =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager = LinearLayoutManager(this)
        binding.rvCategoryList.layoutManager = layoutManagerCategoriesShop
        binding.rvCategoryList.setHasFixedSize(true)
        binding.rvShopList.layoutManager = layoutManager
        binding.rvShopList.setHasFixedSize(true)
        shopsListViewModel.getShops()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val item = menu.findItem(R.id.action_search)

        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handleSearch(newText)
                return true
            }
        })

        item?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                shopAdapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> shopsListViewModel.getShops()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        //if (query.isNotEmpty()) {
        binding.pbLoading.visibility = VISIBLE
        shopsListViewModel.onSearchClick(query)
        //}
    }

    private fun filterByCategory(category: String) {
        shopsListViewModel.params.category = category
        shopAdapter.filter.filter(category)
    }

    private fun updateShops(totalShops: Int) {
        binding.txtTotalShops.text = totalShops.toString()
    }

    private fun bindListData(shops: Shops) {
        if (!(shops.shopsList.isNullOrEmpty())) {
            shopAdapter = ShopsAdapter(
                shopsListViewModel,
                shops.shopsList
            )
            binding.rvShopList.adapter = shopAdapter
            binding.txtTotalShops.text = shops.shopsList.size.toString()
            drawCategories(shops)
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun drawCategories(shops: Shops) {
        val categories: MutableList<String> = mutableListOf()
        if (!(shops.shopsList.isNullOrEmpty())) {
            shops.shopsList.forEach {
                if (!categories.contains(it.category)) {
                    if (it.category != null && it.category.isNotEmpty()) {
                        categories.add(it.category)
                    }
                }

            }
            categories.add(0, ALL_CATEGORY)
            shopCategoryAdapter = ShopCategoryAdapter(shopsListViewModel, categories)
            binding.rvCategoryList.adapter = shopCategoryAdapter
        }

    }

    private fun navigateToDetailsScreen(shopItem: ShopsItem) {
        val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(SHOP_ITEM_KEY, shopItem)
        }
        startActivity(nextScreenIntent)
    }


    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        shopsListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvShopList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvShopList.toGone()
    }


    private fun showSearchResult(query: String) {
        shopAdapter.filter.filter(query)
        binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleCategoriesList(status: Resource<Shops>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(shops = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { shopsListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(shopsListViewModel.shopsLiveData, ::handleCategoriesList)
        observe(shopsListViewModel.searchFound, ::showSearchResult)
        observe(shopsListViewModel.noSearchFound, ::noSearchResult)
        observe(shopsListViewModel.totalShopsData, ::updateShops)
        observe(shopsListViewModel.filterByCategoriesData, ::filterByCategory)
        observe(shopsListViewModel.openShopDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(shopsListViewModel.showSnackBar)
        observeToast(shopsListViewModel.showToast)

    }
}
