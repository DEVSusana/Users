package com.devsusana.users.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.userById.UserById
import com.devsusana.users.data.model.userById.UserDataById
import com.devsusana.users.data.model.userById.UserSupportById
import com.devsusana.users.data.utils.Resource
import com.devsusana.users.domain.usecase.GetDetailUserUseCase
import com.devsusana.users.domain.usecase.GetListUsersUseCase
import com.devsusana.users.view.pagin.ResultDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var getDetailUserUseCase: GetDetailUserUseCase

    @Mock
    private lateinit var getListUsersUseCase: GetListUsersUseCase
    @Mock
    private lateinit var apiService: ApiService

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ViewModel

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = ViewModel(application, apiService,
            getListUsersUseCase, getDetailUserUseCase)
        viewModel.dispatcher = dispatcher
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test isNetworkAvailable returns true`() {
        // Mock the connectivity manager and network capabilities
        val context = mock<Context>()
        val connectivityManager = mock(ConnectivityManager::class.java)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        val networkCapabilities = mock(NetworkCapabilities::class.java)
        `when`(connectivityManager.activeNetwork)
            .thenReturn(mock())
        `when`(connectivityManager.getNetworkCapabilities(any()))
            .thenReturn(networkCapabilities)

        // Set up the desired network capabilities for the test case
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(true)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            .thenReturn(true)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)

        // Invoke the function being tested
        val result = viewModel.isNetworkAvailable(context)

        // Verify the result
        assertTrue(result)
    }

    @Test
    fun `test isNetworkAvailable returns false when context is null`() {
        val result = viewModel.isNetworkAvailable(null)

        assertEquals(false, result)
    }

    @Test
    fun `test invalidateResultDataSource`() {
        val mockResultDataSource = mock<ResultDataSource>()

        viewModel.resultDataSource = mockResultDataSource
        viewModel.invalidateResultDataSource()

        verify(mockResultDataSource).invalidate()
    }

    @Test
    fun `getCharacterDetailResponse should post Loading state and fetch data when network is available`() =
        runTest {
            // Mock network availability
            val context = mock<Context>()
            val connectivityManager = mock(ConnectivityManager::class.java)
            `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(connectivityManager)
            val networkCapabilities = mock(NetworkCapabilities::class.java)
            `when`(connectivityManager.activeNetwork)
                .thenReturn(mock())
            `when`(connectivityManager.getNetworkCapabilities(any()))
                .thenReturn(networkCapabilities)

            // Set up the desired network capabilities for the test case
            `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                .thenReturn(true)
            `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                .thenReturn(true)
            `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                .thenReturn(true)

            // Set up response
            val user = UserById(
                UserDataById(
                    id = 2,
                    email = "janet.weaver@reqres.in",
                    first_name = "Janet",
                    last_name = "Weaver",
                    avatar = "https://reqres.in/img/faces/2-image.jpg"
                ),
                UserSupportById(
                    text = "To keep ReqRes free, contributions towards server costs are appreciated!",
                    url = "https://reqres.in/#support-heading"
                )
            )
            `when`(getDetailUserUseCase.invoke(Mockito.anyInt())).thenReturn(
                Resource.Success(
                    user
                )
            )
            // Invoke the method
            viewModel.getUserDetailResponse(2,context)
            advanceUntilIdle()

            val userReceived = viewModel.getUserDetail.value?.data

            // Verify the expected behavior
            assertEquals(user, userReceived)
        }

    @Test
    fun `getCharacterDetailResponse should post Error state when network is not available`() =
        runTest {
            // Mock network availability
            val context = mock<Context>()
            val connectivityManager = mock(ConnectivityManager::class.java)
            `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(connectivityManager)
            val networkCapabilities = mock(NetworkCapabilities::class.java)
            `when`(connectivityManager.activeNetwork)
                .thenReturn(mock())
            `when`(connectivityManager.getNetworkCapabilities(any()))
                .thenReturn(networkCapabilities)

            // Set up the desired network capabilities for the test case
            `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                .thenReturn(false)

            // Invoke the method
            viewModel.getUserDetailResponse(2,context)
            advanceUntilIdle()

            assert(viewModel.getUserDetail.value is Resource.Error)
    }

    @Test
    fun `getCharacterDetailResponse should post Error state when return an exception`() =
        runTest {
            // Invoke the method
            viewModel.getUserDetailResponse(2)
            advanceUntilIdle()

            assert(viewModel.getUserDetail.value is Resource.Error)
        }





}
