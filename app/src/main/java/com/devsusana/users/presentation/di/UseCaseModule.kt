package com.devsusana.users.presentation.di

import com.devsusana.users.domain.repository.Repository
import com.devsusana.users.domain.usecase.GetDetailUserUseCase
import com.devsusana.users.domain.usecase.GetListUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetListUsersUseCase(repository: Repository)
    : GetListUsersUseCase {
        return GetListUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailUserUseCase(repository: Repository)
    : GetDetailUserUseCase {
        return GetDetailUserUseCase(repository)
    }

}