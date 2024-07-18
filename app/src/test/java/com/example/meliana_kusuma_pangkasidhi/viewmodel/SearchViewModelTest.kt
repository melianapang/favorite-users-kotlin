package com.example.meliana_kusuma_pangkasidhi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.database.entities.SearchHistoryEntity
import com.example.core.usecase.SearchUsersUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val useCase: SearchUsersUseCase= mock(SearchUsersUseCase::class.java)

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getStates() {
        runBlocking {
            val keywords = listOf(
                SearchHistoryEntity(
                    id = 1,
                    keyword = "tay"
                )
            )

            Mockito.`when`(useCase.getHistorySearchKeywords()).thenReturn(keywords)

            val vm = SearchViewModel(useCase)

            val errorState = vm.errorState
            val loadingState = vm.isLoading
            val userState = vm.usersState.value
            val historyState = vm.searchHistoryState

            Truth.assertThat(loadingState).isFalse()
            Truth.assertThat(userState).isNull()
            Truth.assertThat(errorState).isFalse()
            Truth.assertThat(historyState?.historyData?.size).isEqualTo(1)
            Truth.assertThat(historyState?.historyData?.first()).isEqualTo("tay")

            Mockito.verify(useCase, Mockito.times(1))?.getHistorySearchKeywords()
        }
    }

    @Test
    fun getHistorySearch() {
        runBlocking {
            val keywords = listOf(
                SearchHistoryEntity(
                    id = 1,
                    keyword = "tay"
                )
            )

            Mockito.`when`(useCase.getHistorySearchKeywords()).thenReturn(keywords)

            val vm = SearchViewModel(useCase)

            val state = vm.searchHistoryState
            Truth.assertThat(state?.historyData?.size).isEqualTo(1)
            Truth.assertThat(state?.historyData?.first()).isEqualTo("tay")

            Mockito.verify(useCase, Mockito.times(1))?.getHistorySearchKeywords()
        }
    }

    @Test
    fun getHistorySearch_error() {
        runBlocking {
            Mockito.`when`(useCase.getHistorySearchKeywords()).thenThrow(RuntimeException("Test Error"))

            val vm = SearchViewModel(useCase)

            val state = vm.searchHistoryState
            Truth.assertThat(state?.historyData?.size).isEqualTo(0)

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            Mockito.verify(useCase, Mockito.times(1))?.getHistorySearchKeywords()
        }
    }

    @Test
    fun clearErrorState() {
        runBlocking {
            Mockito.`when`(useCase.getHistorySearchKeywords()).thenThrow(RuntimeException("Test Error"))

            val vm = SearchViewModel(useCase)

            val state = vm.searchHistoryState
            Truth.assertThat(state?.historyData?.size).isEqualTo(0)

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            vm.clearErrorState()

            val errorState2 = vm.errorState
            Truth.assertThat(errorState2).isFalse()
        }
    }
}