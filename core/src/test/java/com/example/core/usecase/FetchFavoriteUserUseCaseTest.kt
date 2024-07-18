package com.example.core.usecase

import com.example.core.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FetchFavoriteUserUseCaseTest {

    @Mock
    lateinit var repository: UserRepository

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getFavoriteUsers() {
        val useCase = FetchFavoriteUsersUseCase(repository)

        runBlocking {
            val res = useCase.getFavoriteUsers()
            Mockito.verify(repository, Mockito.times(1)).getFavoriteUsers()
        }
    }

    @Test
    fun updateFavorite() {
        val useCase = FetchFavoriteUsersUseCase(repository)
        val id = 11

        runBlocking {
            val res = useCase.updateFavorite(id, true)
            Mockito.verify(repository, Mockito.times(1)).updateUserFavoriteStatus(id, true)
        }
    }
}