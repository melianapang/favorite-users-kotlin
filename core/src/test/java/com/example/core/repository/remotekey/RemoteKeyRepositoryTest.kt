package com.example.core.repository.remotekey

import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.entities.RemoteKeyEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RemoteKeyRepositoryTest {

    @Mock
    lateinit var remoteKeyDao: RemoteKeyDao

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getLastUpdatedTime() {
        val repo = RemoteKeyRepository(remoteKeyDao)

        runBlocking {
            val res = repo.getLastUpdatedTime()
            Mockito.verify(remoteKeyDao, Mockito.times(1)).getLastUpdated()
        }
    }

    @Test
    fun getLastUserRemoteKeyById() {
        val repo = RemoteKeyRepository(remoteKeyDao)

        runBlocking {
            val res = repo.getLastUserRemoteKey()
            Mockito.verify(remoteKeyDao, Mockito.times(1)).getLastRemoteKey()
        }
    }

    @Test
    fun insertRemoteKey() {
        val repo = RemoteKeyRepository(remoteKeyDao)
        val remoteKeyEntity = RemoteKeyEntity(
            id = 11,
            nextKey = 12,
            prevKey = 13,
        )

        runBlocking {
            val res = repo.insertRemoteKey(remoteKeyEntity)
            Mockito.verify(remoteKeyDao, Mockito.times(1)).insertOrReplace(remoteKeyEntity)
        }
    }

    @Test
    fun clearRemoteKeys() {
        val repo = RemoteKeyRepository(remoteKeyDao)

        runBlocking {
            val res = repo.clearRemoteKeys()
            Mockito.verify(remoteKeyDao, Mockito.times(1)).clearRemoteKeys()
        }
    }
}