package com.devsusana.users.domain.usecase

import com.devsusana.users.domain.repository.Repository

class GetDetailUserUseCase (private val repository: Repository) {
    suspend fun invoke(seed: String) = repository.getUserById(seed)
}