package com.devsusana.users.data.api

import com.devsusana.users.data.model.ApiResponse
import com.devsusana.users.data.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): Response<ApiResponse>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<Data>

}