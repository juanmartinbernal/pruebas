package com.juanmartin.videos.ui.component.videos

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juanmartin.videos.data.DataRepositorySource
import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.videos.CategoryItem
import com.juanmartin.videos.data.dto.videos.VideoDataResult
import com.juanmartin.videos.ui.base.BaseViewModel
import com.juanmartin.videos.utils.SingleEvent
import com.juanmartin.videos.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideosCategoryListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val categoriesLiveDataPrivate = MutableLiveData<Resource<VideoDataResult>>()
    val categoriesLiveData: LiveData<Resource<VideoDataResult>> get() = categoriesLiveDataPrivate


    //TODO check to make them as one Resource
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val searchFoundPrivate: MutableLiveData<CategoryItem> = MutableLiveData()
    val searchFound: LiveData<CategoryItem> get() = searchFoundPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openCategorieDetailsPrivate = MutableLiveData<SingleEvent<CategoryItem>>()
    val openCategorieDetails: LiveData<SingleEvent<CategoryItem>> get() = openCategorieDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getVideos() {
        viewModelScope.launch {
            categoriesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestVideos().collect {
                    categoriesLiveDataPrivate.value = it
                }
            }
        }
    }

    fun openCategoryDetails(categoryItem: CategoryItem) {
        openCategorieDetailsPrivate.value = SingleEvent(categoryItem)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}
