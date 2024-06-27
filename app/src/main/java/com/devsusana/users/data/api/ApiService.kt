package com.devsusana.users.data.api

import com.devsusana.users.data.model.listuser.ApiResponse
import com.devsusana.users.data.model.user.DataId
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ApiResponse>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<DataId>

}