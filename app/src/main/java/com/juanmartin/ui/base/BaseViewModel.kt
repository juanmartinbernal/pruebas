package com.juanmartin.ui.base

import androidx.lifecycle.ViewModel
import com.juanmartin.usecase.erros.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}