package com.example.core.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.api.model.UserResponse
import com.example.core.database.entities.UserEntity
import com.example.core.repository.user.UserRemoteMediator
import com.example.core.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userRemoteMediator: UserRemoteMediator,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun fetchUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 30,
            ),
            pagingSourceFactory = {
                userRepository.getUsers()
            },
            remoteMediator = userRemoteMediator
        ).flow
    }

    suspend fun fetchAndSaveUserByUsername(username: String): UserResponse? {
        return userRepository.fetchAndUpdateSavedUserByUsername(username)
    }
}