package com.juanmartin.videos.ui.base

import androidx.lifecycle.ViewModel
import com.juanmartin.videos.usecase.erros.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}