package com.example.core.repository.historysearch

import com.example.core.database.dao.SearchHistoryDao
import com.example.core.database.entities.SearchHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) {
    suspend fun getHistorySearchKeywords(): List<SearchHistoryEntity> {
        return withContext(Dispatchers.IO) {
            searchHistoryDao.getAllHistory()
        }
    }

    suspend fun getHistoryByKeyword(keyword: String): List<SearchHistoryEntity> {
        return withContext(Dispatchers.IO) {
            searchHistoryDao.getHistoryByKeyword(keyword)
        }
    }

    suspend fun saveAndGetHistorySearchKeyword(data: SearchHistoryEntity): List<SearchHistoryEntity> {
        return withContext(Dispatchers.IO) {
            searchHistoryDao.insert(data)
            searchHistoryDao.getAllHistory()
        }
    }
}