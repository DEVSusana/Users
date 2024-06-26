package com.devsusana.users.presentation.di


import com.devsusana.users.data.repository.RepositoryImpl
import com.devsusana.users.data.repository.dataSource.RemoteDataSource
import com.devsusana.users.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource)
    }
}