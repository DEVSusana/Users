package com.devsusana.users.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.ApiResponse
import com.devsusana.users.data.model.Result
import com.devsusana.users.data.repository.dataSource.RemoteDataSource
import com.devsusana.users.data.utils.Resource
import com.devsusana.users.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : Repository {
    override suspend fun getListOfUsers(pager: Pager<Int, Result>): Flow<PagingData<Result>> {
        return pager.flow
    }

    override suspend fun getUserById(seed:String): Resource<ApiResponse> {
        return responseToResource(remoteDataSource.getUserById(seed))
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}