package com.devsusana.users.data.repository.dataSourceImpl

import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.user.DataId
import com.devsusana.users.data.repository.dataSource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getUserById(id: Int): Response<DataId> {
        return apiService.getUserById(id)
    }
}