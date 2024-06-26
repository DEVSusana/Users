package com.devsusana.users.data.api

import com.devsusana.users.data.model.ApiResponse
import com.devsusana.users.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("")
    suspend fun getUserList(
        @Query("page") page: Int
    ): Response<ApiResponse>

    @GET("")
    suspend fun getUserById(
        @Query("seed") seed: String
    ): Response<ApiResponse>

}