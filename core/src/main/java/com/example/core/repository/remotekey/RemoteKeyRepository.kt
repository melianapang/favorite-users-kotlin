package com.example.core.repository.remotekey

import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.entities.RemoteKeyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteKeyRepository @Inject constructor(
    private val remoteKeyDao: RemoteKeyDao,
) {
    suspend fun getLastUpdatedTime(): Long {
        return withContext(Dispatchers.IO) {
            remoteKeyDao.getLastUpdated()
        }
    }

    suspend fun getLastUserRemoteKey(): RemoteKeyEntity? {
        return withContext(Dispatchers.IO) {
            remoteKeyDao.getLastRemoteKey()
        }
    }

    suspend fun insertRemoteKey(remoteKey: RemoteKeyEntity) {
        withContext(Dispatchers.IO) {
            remoteKeyDao.insertOrReplace(remoteKey)
        }
    }
    suspend fun clearRemoteKeys() {
        withContext(Dispatchers.IO) {
            remoteKeyDao.clearRemoteKeys()
        }
    }
}