package com.devsusana.users.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsusana.users.data.api.ApiService
import com.devsusana.users.domain.usecase.GetDetailUserUseCase
import com.devsusana.users.domain.usecase.GetListUsersUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val app: Application,
    private val apiService: ApiService,
    private val getListUsersUseCase: GetListUsersUseCase,
    private val getDetailUserUseCase: GetDetailUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            Application::class.java,
            GetListUsersUseCase::class.java,
            GetDetailUserUseCase::class.java,
            ApiService::class.java
        ).newInstance(
            app,
            getListUsersUseCase,
            getDetailUserUseCase,
            apiService
        )
    }

}