package com.example.core.repository.user

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.database.entities.UserEntity
import com.example.core.repository.remotekey.RemoteKeyRepository
import com.example.core.utilities.extractRemoteKeyEntity
import com.example.core.utilities.toUserEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val userRepository: UserRepository,
    private val remoteKeyRepo: RemoteKeyRepository,
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if (System.currentTimeMillis() - remoteKeyRepo.getLastUpdatedTime() >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    userRepository.clearUsers()
                    0
                }

                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val lastId: Int
                    val lastItem = state.lastItemOrNull()

                    lastId = if (lastItem == null) 0
                    else {
                        val remoteKey = remoteKeyRepo.getLastUserRemoteKey()
                            ?: return MediatorResult.Success(
                                endOfPaginationReached = true
                            )
                        remoteKey.id
                    }
                    lastId
                }

                else -> 0
            }

            val response = userRepository.fetchUsers(cursor = loadKey)
            if (!response.isNullOrEmpty()) {
                val users = response.toUserEntity()
                val remoteKey = response.last().extractRemoteKeyEntity()
                userRepository.insertUsers(users)
                remoteKeyRepo.insertRemoteKey(remoteKey)
            }

            MediatorResult.Success(
                endOfPaginationReached = response?.isEmpty() == true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
