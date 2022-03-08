package com.juanmartin.videos.ui.component.videos

import android.app.SearchManager
import android.content.Context
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
import com.juanmartin.videos.R
import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.videos.CategoryItem
import com.juanmartin.videos.data.dto.videos.VideoDataResult
import com.juanmartin.videos.data.error.SEARCH_ERROR
import com.juanmartin.videos.databinding.CategoryActivityBinding
import com.juanmartin.videos.ui.base.BaseActivity
import com.juanmartin.videos.ui.component.videos.adapter.VideoCategoryAdapter
import com.juanmartin.videos.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideosCategoryListActivity : BaseActivity() {
    private lateinit var binding: CategoryActivityBinding

    private val videosCategoryListViewModel: VideosCategoryListViewModel by viewModels()
    private lateinit var categoryAdapter: VideoCategoryAdapter

    override fun initViewBinding() {
        binding = CategoryActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.videos_category)
        val layoutManager = LinearLayoutManager(this)
        binding.rvCategoryList.layoutManager = layoutManager
        binding.rvCategoryList.setHasFixedSize(true)
        videosCategoryListViewModel.getVideos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> videosCategoryListViewModel.getVideos()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = VISIBLE
            // videosCategoryListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(videos: VideoDataResult) {
        if (!(videos.videosList.isNullOrEmpty())) {
            categoryAdapter = VideoCategoryAdapter(
                videosCategoryListViewModel,
                videos.videosList.get(0).categories
            )
            binding.rvCategoryList.adapter = categoryAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<CategoryItem>) {

    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        videosCategoryListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvCategoryList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvCategoryList.toGone()
    }


    private fun showSearchResult(categoryItem: CategoryItem) {
        videosCategoryListViewModel.openCategoryDetails(categoryItem)
        binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleCategoriesList(status: Resource<VideoDataResult>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(videos = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { videosCategoryListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(videosCategoryListViewModel.categoriesLiveData, ::handleCategoriesList)
        observe(videosCategoryListViewModel.searchFound, ::showSearchResult)
        observe(videosCategoryListViewModel.noSearchFound, ::noSearchResult)
        observeEvent(videosCategoryListViewModel.openCategorieDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(videosCategoryListViewModel.showSnackBar)
        observeToast(videosCategoryListViewModel.showToast)

    }
}
