package com.devsusana.users.presentation.di


import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.repository.dataSource.RemoteDataSource
import com.devsusana.users.data.repository.dataSourceImpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
}