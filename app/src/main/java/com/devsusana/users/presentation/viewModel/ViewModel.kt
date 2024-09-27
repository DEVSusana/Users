package com.devsusana.users.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.data.model.listuser.UserDataList
import com.devsusana.users.data.model.userById.UserById
import com.devsusana.users.data.utils.Resource
import com.devsusana.users.domain.usecase.GetDetailUserUseCase
import com.devsusana.users.domain.usecase.GetListUsersUseCase
import com.devsusana.users.view.pagin.ResultDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val app: Application,
    private val apiService: ApiService,
    private val getListUsersUseCase: GetListUsersUseCase,
    private val getDetailUserUseCase: GetDetailUserUseCase
): AndroidViewModel(app){

    lateinit var resultDataSource: ResultDataSource

    var dispatcher: CoroutineDispatcher = Dispatchers.IO


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities.isNetworkCapabilitiesValid()

    }

    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true

        else -> false
    }


    fun invalidateResultDataSource() {
        resultDataSource.invalidate()
    }

    val resultUserList: Flow<PagingData<UserDataList>> = flow {
        val usersList = getListUsersUseCase.invoke(
            Pager(PagingConfig(pageSize = 50)) {
                ResultDataSource(apiService).also { resultDataSource = it }
            }
        )
        emitAll(usersList)
    }.cachedIn(viewModelScope)


    private val _getUserDetail: MutableLiveData<Resource<UserById>> by lazy {
        MutableLiveData<Resource<UserById>>()
    }

    val getUserDetail: LiveData<Resource<UserById>> get() = _getUserDetail

    fun getUserDetailResponse(id: Int, context: Context? = app) =
        viewModelScope.launch(dispatcher) {
            _getUserDetail.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(context)) {
                    val apiResult = getDetailUserUseCase.invoke(id)
                    _getUserDetail.postValue(apiResult)
                } else {
                    _getUserDetail.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                _getUserDetail.postValue(Resource.Error(e.message.toString()))
            }
        }


}