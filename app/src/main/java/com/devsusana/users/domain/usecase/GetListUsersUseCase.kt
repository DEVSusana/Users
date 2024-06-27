package com.devsusana.users.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.listuser.Data
import com.devsusana.users.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetListUsersUseCase(private val repository: Repository) {
    suspend fun invoke(pager: Pager<Int, Data>): Flow<PagingData<Data>>{
        return repository.getListOfUsers(pager)

    }
}