package com.juanmartin.ui.component.shops

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juanmartin.data.DataRepositorySource
import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.ui.base.BaseViewModel
import com.juanmartin.ui.component.shops.entities.ParamFilter
import com.juanmartin.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopsListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    val params = ParamFilter(0.0, 0.0, "")

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val shopsLiveDataPrivate = MutableLiveData<Resource<Shops>>()
    val shopsLiveData: LiveData<Resource<Shops>> get() = shopsLiveDataPrivate

    val filterByCategoriesPrivate = MutableLiveData<String>()
    val filterByCategoriesData: LiveData<String> get() = filterByCategoriesPrivate

    val totalShopsPrivate = MutableLiveData<Int>()
    val totalShopsData: LiveData<Int> get() = totalShopsPrivate

    //TODO check to make them as one Resource
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val searchFoundPrivate: MutableLiveData<String> = MutableLiveData()
    val searchFound: LiveData<String> get() = searchFoundPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openShopDetailsPrivate = MutableLiveData<ShopsItem>()
    val openShopDetails: LiveData<ShopsItem> get() = openShopDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getShops() {
        viewModelScope.launch {
            shopsLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.requestShops(params).collect {
                shopsLiveDataPrivate.value = it
            }
        }
    }

    fun openShopDetails(shopItem: ShopsItem) {
        openShopDetailsPrivate.value = shopItem
    }

    fun filterByCategories(category: String) {
        filterByCategoriesPrivate.value = category
    }

    fun updateShops(totalShops: Int) {
        totalShopsPrivate.value = totalShops
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun onSearchClick(query: String) {
        searchFoundPrivate.value = query
    }
}
