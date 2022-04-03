package com.juanmartin.usecase.erros
import com.juanmartin.data.error.Error;

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}