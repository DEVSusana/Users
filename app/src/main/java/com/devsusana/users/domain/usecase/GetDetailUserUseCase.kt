package com.devsusana.users.domain.usecase

import com.devsusana.users.data.model.userMock.UserById
import com.devsusana.users.data.utils.Resource
import com.devsusana.users.domain.repository.Repository

class GetDetailUserUseCase (private val repository: Repository) {
    suspend fun invoke(id: Int): Resource<UserById> {
        return repository.getUserById(id)
    }
}