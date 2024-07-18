package com.example.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.api.model.UserResponse
import com.example.core.database.entities.SearchHistoryEntity
import com.example.core.repository.historysearch.SearchHistoryRepository
import com.example.core.repository.user.SearchUserPagingSource
import com.example.core.repository.user.SearchUserPagingSource.SearchUserParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val searchUserPagingSource: SearchUserPagingSource,
    private val historySearchRepo: SearchHistoryRepository
) {
    fun searchUsers(keyword: String): Flow<PagingData<UserResponse>> {
        searchUserPagingSource.param = SearchUserParam(
            keyword = keyword
        )

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 30,
            ),
            pagingSourceFactory = {
                searchUserPagingSource
            }).flow
    }

    suspend fun getHistorySearchKeywords(): List<SearchHistoryEntity> {
        return historySearchRepo.getHistorySearchKeywords()
    }

    suspend fun saveAndGetHistorySearchKeyword(keyword: String): List<SearchHistoryEntity>? {
        if (historySearchRepo.getHistoryByKeyword(keyword).isNotEmpty()) return null
        return historySearchRepo.saveAndGetHistorySearchKeyword(
            SearchHistoryEntity(keyword = keyword)
        )
    }
}