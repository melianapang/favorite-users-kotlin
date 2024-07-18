package com.example.core.repository.historysearch

import com.example.core.database.dao.SearchHistoryDao
import com.example.core.database.entities.SearchHistoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchHistoryRepositoryTest {

    @Mock
    lateinit var searchHistoryDao: SearchHistoryDao

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getNotificationSettingsData() {
        val repo = SearchHistoryRepository(searchHistoryDao)

        runBlocking {
            val res = repo.getHistorySearchKeywords()
            Mockito.verify(searchHistoryDao, Mockito.times(1)).getAllHistory()
        }
    }

    @Test
    fun getHistoryByKeyword() {
        val repo = SearchHistoryRepository(searchHistoryDao)

        runBlocking {
            val res = repo.getHistoryByKeyword("taylor")
            Mockito.verify(searchHistoryDao, Mockito.times(1)).getHistoryByKeyword("taylor")
        }
    }

    @Test
    fun saveAndGetHistorySearchKeyword() {
        val repo = SearchHistoryRepository(searchHistoryDao)
        val searchHistoryEntity = SearchHistoryEntity(
            id = 1,
            keyword = "taylor",
        )

        runBlocking {
            val res = repo.saveAndGetHistorySearchKeyword(searchHistoryEntity)
            Mockito.verify(searchHistoryDao, Mockito.times(1)).insert(searchHistoryEntity)
            Mockito.verify(searchHistoryDao, Mockito.times(1)).getAllHistory()
        }
    }
}