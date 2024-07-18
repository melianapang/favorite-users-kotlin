package com.example.core.usecase

import com.example.core.database.entities.SearchHistoryEntity
import com.example.core.repository.historysearch.SearchHistoryRepository
import com.example.core.repository.user.SearchUserPagingSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchUsersUseCaseTest {

    @Mock
    lateinit var pagingSource: SearchUserPagingSource

    @Mock
    lateinit var repository: SearchHistoryRepository

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getHistorySearchKeywords() {
        val useCase = SearchUsersUseCase(pagingSource, repository)

        runBlocking {
            val res = useCase.getHistorySearchKeywords()
            Mockito.verify(repository, Mockito.times(1)).getHistorySearchKeywords()
        }
    }

    @Test
    fun saveAndGetHistorySearchKeyword_newData() {
        val useCase = SearchUsersUseCase(pagingSource, repository)
        val keyword = "tay"
        val searchHistoryEntity = SearchHistoryEntity(keyword = keyword)

        runBlocking {
            Mockito.`when`(repository.getHistoryByKeyword(keyword)).thenReturn(listOf())

            val res = useCase.saveAndGetHistorySearchKeyword(keyword)
            Mockito.verify(repository, Mockito.times(1)).getHistoryByKeyword(keyword)
            Mockito.verify(repository, Mockito.times(1)).saveAndGetHistorySearchKeyword(searchHistoryEntity)
        }
    }

    @Test
    fun saveAndGetHistorySearchKeyword_alreadySaved() {
        val useCase = SearchUsersUseCase(pagingSource, repository)
        val keyword = "tay"
        val searchHistoryEntity = SearchHistoryEntity(keyword = keyword)

        runBlocking {
            Mockito.`when`(repository.getHistoryByKeyword(keyword)).thenReturn(listOf(searchHistoryEntity))

            val res = useCase.saveAndGetHistorySearchKeyword(keyword)
            Mockito.verify(repository, Mockito.times(1)).getHistoryByKeyword(keyword)
            Mockito.verify(repository, Mockito.times(0)).saveAndGetHistorySearchKeyword(searchHistoryEntity)
        }
    }
}