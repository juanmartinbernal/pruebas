package com.juanmartin.ui.component.shops.details


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class DetailsViewModel @Inject constructor() :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val shopPrivate = MutableLiveData<ShopsItem>()
    val shopData: LiveData<ShopsItem> get() = shopPrivate


    fun initIntentData(shopItem: ShopsItem?) {
        shopPrivate.value = shopItem!!
    }
}
