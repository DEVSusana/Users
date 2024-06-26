package com.devsusana.users.data.repository.dataSourceImpl

import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.ApiResponse
import com.devsusana.users.data.repository.dataSource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getUserById(seed: String): Response<ApiResponse> {
        return apiService.getUserById(seed)
    }
}