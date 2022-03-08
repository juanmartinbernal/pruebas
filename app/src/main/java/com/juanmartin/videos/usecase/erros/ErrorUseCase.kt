package com.juanmartin.videos.usecase.erros
import com.juanmartin.videos.data.error.Error;

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}