package com.juanmartin.videos.ui.component.shops.details


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanmartin.videos.data.dto.comercios.ShopsItem
import com.juanmartin.videos.ui.base.BaseViewModel
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
