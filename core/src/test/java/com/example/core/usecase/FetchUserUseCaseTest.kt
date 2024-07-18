package com.example.core.usecase

import com.example.core.api.model.UserResponse
import com.example.core.repository.user.UserRemoteMediator
import com.example.core.repository.user.UserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FetchUserUseCaseTest {

    @Mock
    lateinit var repository: UserRepository

    @Mock
    lateinit var remoteMediator: UserRemoteMediator

    private val mockUserResponse = UserResponse(
        id = 11,
        name = "name",
        company = "OP",
        login = "taylor",
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

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun fetchAndSaveUserByUsername() {
        val useCase = FetchUsersUseCase(repository, remoteMediator)

        runBlocking {
            Mockito.`when`(repository.fetchAndUpdateSavedUserByUsername(mockUserResponse.login)).thenReturn(mockUserResponse)

            val res = useCase.fetchAndSaveUserByUsername(mockUserResponse.login)
            Truth.assertThat(res?.login).isEqualTo(mockUserResponse.login)
            Mockito.verify(repository, Mockito.times(1)).fetchAndUpdateSavedUserByUsername(mockUserResponse.login)
        }
    }
}
