package com.devsusana.users.view.pagin

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ResultDataSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            val nextPage = params.key ?: 1
            val characterList = apiService
                .getUserList(nextPage)

            return LoadResult.Page(
                data = characterList.body()?.results.orEmpty(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
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