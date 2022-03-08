package com.juanmartin.videos.di


import com.juanmartin.videos.data.error.mapper.ErrorMapper
import com.juanmartin.videos.data.error.mapper.ErrorMapperSource
import com.juanmartin.videos.usecase.erros.ErrorManager
import com.juanmartin.videos.usecase.erros.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
