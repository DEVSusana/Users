package com.devsusana.users.data.repository.dataSource

import com.devsusana.users.data.model.ApiResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getUserById(seed: String): Response<ApiResponse>
}