package com.devsusana.users.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsusana.users.data.model.listuser.UserDataList
import com.devsusana.users.data.model.userMock.UserById
import com.devsusana.users.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getListOfUsers(pager: Pager<Int, UserDataList>): Flow<PagingData<UserDataList>>

    suspend fun getUserById(id: Int): Resource<UserById>

}