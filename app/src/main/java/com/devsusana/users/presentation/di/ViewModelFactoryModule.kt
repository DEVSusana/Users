package com.devsusana.users.presentation.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.domain.usecase.GetDetailUserUseCase
import com.devsusana.users.domain.usecase.GetListUsersUseCase
import com.devsusana.users.presentation.viewModel.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelFactoryModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        app: Application,
        apiService: ApiService,
        getListUsersUseCase: GetListUsersUseCase,
        getDetailUserUseCase: GetDetailUserUseCase
    ): ViewModelProvider.Factory {
        return ViewModelFactory(app, apiService, getListUsersUseCase, getDetailUserUseCase)
    }
}