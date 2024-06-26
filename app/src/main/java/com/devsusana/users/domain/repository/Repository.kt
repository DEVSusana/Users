package com.devsusana.users.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.Data
import com.devsusana.users.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getListOfUsers(pager: Pager<Int, Data>): Flow<PagingData<Data>>

    suspend fun getUserById(id: Int): Resource<Data>

}