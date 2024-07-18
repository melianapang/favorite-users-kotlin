package com.example.meliana_kusuma_pangkasidhi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.database.entities.UserEntity
import com.example.core.usecase.FetchFavoriteUsersUseCase
import com.example.core.usecase.FetchUsersUseCase
import com.example.meliana_kusuma_pangkasidhi.model.User
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
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val fetchUserUseCase: FetchUsersUseCase = Mockito.mock(FetchUsersUseCase::class.java)
    private val fetchFavoriteUsersUseCase: FetchFavoriteUsersUseCase = Mockito.mock(FetchFavoriteUsersUseCase::class.java)

    private val mockUserEntity = UserEntity(
        id = 11,
        username = "taylor",
        avatarUrl = "https://www.abc.com",
        url = "https://www.abc.com",
        isFavorite = false,
    )

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchFavoriteUsers() {
        val vm = ListViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)
        val mockUser = User(
            id = mockUserEntity.id,
            username = mockUserEntity.username,
            avatarUrl = mockUserEntity.avatarUrl,
            isFavorite = mockUserEntity.isFavorite,
        )

        runBlocking {
            Mockito.`when`(fetchFavoriteUsersUseCase.getFavoriteUsers()).thenReturn(listOf(mockUserEntity))

            vm.getFavoriteUsers()

            val state = vm.favoriteUsersState
            Truth.assertThat(state?.size).isEqualTo(1)
            Truth.assertThat(state?.first()).isEqualTo(mockUser)
        }
    }

    @Test
    fun fetchFavoriteUsers_error() {
        val vm = ListViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

        runBlocking {
            Mockito.`when`(fetchFavoriteUsersUseCase.getFavoriteUsers()).thenThrow(RuntimeException("Test Error"))

            vm.getFavoriteUsers()

            val state = vm.favoriteUsersState
            Truth.assertThat(state?.size).isNull()

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()
        }
    }

    @Test
    fun clearErrorState() {
        val vm = ListViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

        runBlocking {
            Mockito.`when`(fetchFavoriteUsersUseCase.getFavoriteUsers()).thenThrow(RuntimeException("Test Error"))

            vm.getFavoriteUsers()

            val state = vm.favoriteUsersState
            Truth.assertThat(state?.size).isNull()

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            vm.clearErrorState()

            val errorState2 = vm.errorState
            Truth.assertThat(errorState2).isFalse()
        }
    }
}