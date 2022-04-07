package com.juanmartin.ui.component.shops

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
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
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    val PERMISSION_ID = 42

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
        binding.lnyTotalNearShops.setOnClickListener { shopAdapter.orderNearShops() }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

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
            R.id.action_refresh -> getLastLocation()//shopsListViewModel.getShops(currentLocation)
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

    private fun updateNearShops(totalNearShops: Int) {
        binding.txtTotalNearShops.text = totalNearShops.toString()
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
        binding.tvNoData.visibility = if (show) INVISIBLE else VISIBLE
        binding.rvShopList.visibility = if (show) VISIBLE else INVISIBLE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toInvisible()
        binding.rvShopList.toInvisible()
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


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        shopsListViewModel.getShops(location)
                        //   findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        // findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            shopsListViewModel.getShops(mLastLocation)
            //  findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            //findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun observeViewModel() {
        observe(shopsListViewModel.shopsLiveData, ::handleCategoriesList)
        observe(shopsListViewModel.searchFound, ::showSearchResult)
        observe(shopsListViewModel.noSearchFound, ::noSearchResult)
        observe(shopsListViewModel.totalShopsData, ::updateShops)
        observe(shopsListViewModel.totalNearShopsData, ::updateNearShops)
        observe(shopsListViewModel.filterByCategoriesData, ::filterByCategory)
        observe(shopsListViewModel.openShopDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(shopsListViewModel.showSnackBar)
        observeToast(shopsListViewModel.showToast)

    }
}
