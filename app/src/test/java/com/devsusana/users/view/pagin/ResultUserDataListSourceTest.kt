package com.devsusana.users.view.pagin

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.listuser.UserList
import com.devsusana.users.data.model.listuser.UserDataList
import com.devsusana.users.data.model.listuser.UserSupportList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResultUserDataListSourceTest {

    @Before
    fun setup() {

    }

    @After
    fun tearDown() {

    }


    @Test
    fun `returns character list`() = runBlocking {
        // Mock API response
        val responseBody = UserList(
            data = listOf(
                UserDataList(
                    id = 2,
                    email = "janet.weaver@reqres.in",
                    first_name = "Janet",
                    last_name = "Weaver",
                    avatar = "https://reqres.in/img/faces/2-image.jpg"
                )
            ), 1, 1, UserSupportList("", ""), 1, 1
        )
        val response = Response.success(responseBody)
        val apiService = mock(ApiService::class.java)
        `when`(apiService.getUserList(anyInt(), anyInt())).thenReturn(response)

        // Create the data source
        val dataSource = ResultDataSource(apiService)

        // Create load parameters
        val loadParams = LoadParams.Refresh(0, 1, false)

        // Call the load function
        val result = dataSource.load(loadParams)

        // Verify the expected result
        assert(result is LoadResult.Page)
        val pageResult = result as LoadResult.Page
        assert(pageResult.data == responseBody.data)
    }


    @Test
    fun `load - exception occurs, returns LoadResult Error`() = runBlocking {
        // Mock API response
        val apiService = mock(ApiService::class.java)
        // Create the data source
        val dataSource = ResultDataSource(apiService)

        // Create load parameters
        val loadParams = LoadParams.Refresh(0, 1, false)

        // Call the load function
        val result = dataSource.load(loadParams)

        // Verify the expected result
        assert(result is LoadResult.Error)
        val errorResult = result as LoadResult.Error
        assert(errorResult.throwable is NullPointerException)
    }


}
