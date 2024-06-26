package com.devsusana.users.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.Result
import com.devsusana.users.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetListUsersUseCase(private val repository: Repository) {
    suspend fun invoke(pager: Pager<Int, Result>): Flow<PagingData<Result>>{
        return repository.getListOfUsers(pager)

    }
}