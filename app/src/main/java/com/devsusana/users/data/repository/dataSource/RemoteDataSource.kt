package com.devsusana.users.data.repository.dataSource

import com.devsusana.users.data.model.userById.UserById
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getUserById(id: Int): Response<UserById>
}