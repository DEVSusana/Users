package com.devsusana.users.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.ApiResponse
import com.devsusana.users.data.model.Result
import com.devsusana.users.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getListOfUsers(pager: Pager<Int, Result>): Flow<PagingData<Result>>

    suspend fun getUserById(seed: String): Resource<ApiResponse>

}