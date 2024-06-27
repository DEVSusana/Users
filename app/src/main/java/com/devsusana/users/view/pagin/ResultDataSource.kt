package com.devsusana.users.view.pagin

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.listuser.Data
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ResultDataSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {

            val nextPage = params.key ?: 1
            val usersList = apiService
                .getUserList(nextPage, 6)

            val totalPages = usersList.body()?.total_pages ?: 1

            return LoadResult.Page(
                data = usersList.body()?.data.orEmpty(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if(nextPage < totalPages) nextPage + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            return LoadResult.Error(exception)
        }
    }

}