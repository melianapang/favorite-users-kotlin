package com.example.meliana_kusuma_pangkasidhi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.api.model.UserResponse
import com.example.core.usecase.FetchFavoriteUsersUseCase
import com.example.core.usecase.FetchUsersUseCase
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
class DetailViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val fetchUserUseCase: FetchUsersUseCase = Mockito.mock(FetchUsersUseCase::class.java)
    private val fetchFavoriteUsersUseCase: FetchFavoriteUsersUseCase = Mockito.mock(FetchFavoriteUsersUseCase::class.java)


    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    val mockUserResponse = UserResponse(
        id = 1,
        name = "name",
        company = "OP",
        login = "username",
        avatarUrl = "https://www.abc.com",
        url = "https://www.abc.com",
        blog = "https://www.abc.com",
        location = "Surabaya",
        email = "tay@mail.com",
        bio = "bio",
        publicRepos = 6,
        followers = 5,
        following = 1,
    )

    @Test
    fun getStates() {
        val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

        runBlocking {
            val loadingState = vm.isLoading
            val userState = vm.userState
            val errorState = vm.errorState

            Truth.assertThat(loadingState).isFalse()
            Truth.assertThat(userState).isNull()
            Truth.assertThat(errorState).isFalse()
        }
    }

    @Test
    fun getUserDetail() {
        runBlocking {
            Mockito.`when`(fetchUserUseCase.fetchAndSaveUserByUsername(mockUserResponse.login)).thenReturn(mockUserResponse)
            Mockito.`when`(fetchFavoriteUsersUseCase.isFavoriteUser(mockUserResponse.id)).thenReturn(true)

            val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

            vm.getUserDetail(mockUserResponse.login)

            val state = vm.userState
            Truth.assertThat(state?.id).isEqualTo(mockUserResponse.id)
            Truth.assertThat(state?.username).isEqualTo(mockUserResponse.login)
            Truth.assertThat(state?.avatarUrl).isEqualTo(mockUserResponse.avatarUrl)
            Truth.assertThat(state?.isFavorite).isTrue()

            Mockito.verify(fetchUserUseCase, Mockito.times(1))?.fetchAndSaveUserByUsername(mockUserResponse.login)
            Mockito.verify(fetchFavoriteUsersUseCase, Mockito.times(1))?.isFavoriteUser(mockUserResponse.id)
        }
    }

    @Test
    fun getUserDetail_error() {
        runBlocking {
            Mockito.`when`(fetchUserUseCase.fetchAndSaveUserByUsername(mockUserResponse.login)).thenThrow(RuntimeException("Test Error"))

            val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

            vm.getUserDetail(mockUserResponse.login)

            val state = vm.userState
            Truth.assertThat(state).isNull()

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            Mockito.verify(fetchUserUseCase, Mockito.times(1))?.fetchAndSaveUserByUsername(mockUserResponse.login)
            Mockito.verify(fetchFavoriteUsersUseCase, Mockito.times(0))?.isFavoriteUser(mockUserResponse.id)
        }
    }

    @Test
    fun updateFavorite() {
        runBlocking {
            Mockito.`when`(fetchUserUseCase.fetchAndSaveUserByUsername(mockUserResponse.login)).thenReturn(mockUserResponse)
            Mockito.`when`(fetchFavoriteUsersUseCase.isFavoriteUser(mockUserResponse.id)).thenReturn(true)

            val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

            vm.getUserDetail(mockUserResponse.login)
            vm.updateFavorite()

            Mockito.verify(fetchFavoriteUsersUseCase, Mockito.times(1))?.updateFavorite(mockUserResponse.id, false)
        }
    }

    @Test
    fun updateFavorite_error() {
        runBlocking {
            Mockito.`when`(fetchUserUseCase.fetchAndSaveUserByUsername(mockUserResponse.login)).thenThrow(RuntimeException("Test Error"))

            val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

            vm.getUserDetail(mockUserResponse.login)
            vm.updateFavorite()

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            Mockito.verify(fetchUserUseCase, Mockito.times(1))?.fetchAndSaveUserByUsername(mockUserResponse.login)
        }
    }

    @Test
    fun clearErrorState() {
        runBlocking {

            Mockito.`when`(fetchUserUseCase.fetchAndSaveUserByUsername(mockUserResponse.login)).thenThrow(RuntimeException("Test Error"))

            val vm = DetailViewModel(fetchUserUseCase, fetchFavoriteUsersUseCase)

            vm.getUserDetail(mockUserResponse.login)
            vm.updateFavorite()

            val errorState = vm.errorState
            Truth.assertThat(errorState).isTrue()

            vm.clearErrorState()

            val errorState2 = vm.errorState
            Truth.assertThat(errorState2).isFalse()

            Mockito.verify(fetchUserUseCase, Mockito.times(1))?.fetchAndSaveUserByUsername(mockUserResponse.login)
        }
    }
}