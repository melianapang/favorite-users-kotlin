package com.example.core.datastore

import com.example.core.api.ApiService
import com.example.core.api.model.SearchResultResponse
import com.example.core.api.model.UserResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class UserRemoteDataStoreTest {
    @Mock
    lateinit var apiService: ApiService

    private val mockUserResponse = UserResponse(
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

    private val mockSearchResult = SearchResultResponse(
        totalCount = 1,
        isIncompleteResult = false,
        items = arrayListOf(mockUserResponse)
    )


    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun fetchUsers_normal() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<List<UserResponse>> = Mockito.mock(Call::class.java) as Call<List<UserResponse>>
        val resMock: Response<List<UserResponse>> = Mockito.mock(Response::class.java) as Response<List<UserResponse>>

        Mockito.`when`(apiService.fetchUsers(0)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenReturn(resMock)
        Mockito.`when`(resMock.body()).thenReturn(arrayListOf(mockUserResponse))

        runBlocking {
            val response = remote.fetchUsers(cursor = 0)
            Truth.assertThat(response?.size).isEqualTo(1)
            Truth.assertThat(response?.first()).isEqualTo(mockUserResponse)
        }
    }

    @Test
    fun fetchUsers_errorIO() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<List<UserResponse>> = Mockito.mock(Call::class.java) as Call<List<UserResponse>>
        val resMock: Response<List<UserResponse>> = Mockito.mock(Response::class.java) as Response<List<UserResponse>>

        Mockito.`when`(apiService.fetchUsers(0)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(IOException("Test Error Network"))
        Mockito.`when`(resMock.body()).thenReturn(arrayListOf(mockUserResponse))

        runBlocking {
            try {
                val response = remote.fetchUsers(cursor = 0)
                throw RuntimeException("Wrong Behavior")
            } catch (e: IOException) {
                //expected to be here
            }
        }
    }

    @Test
    fun fetchUsers_errorRuntime() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<List<UserResponse>> = Mockito.mock(Call::class.java) as Call<List<UserResponse>>
        val resMock: Response<List<UserResponse>> = Mockito.mock(Response::class.java) as Response<List<UserResponse>>

        Mockito.`when`(apiService.fetchUsers(0)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(RuntimeException("Test Network Error"))
        Mockito.`when`(resMock.body()).thenReturn(arrayListOf(mockUserResponse))

        runBlocking {
            try {
                val res = remote.fetchUsers(cursor = 0)
                throw RuntimeException("Wrong Behavior")
            } catch (e: RuntimeException) {
                //expected to be here
            }
        }
    }

    @Test
    fun searchUsers_normal() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<SearchResultResponse> = Mockito.mock(Call::class.java) as Call<SearchResultResponse>
        val resMock: Response<SearchResultResponse> = Mockito.mock(Response::class.java) as Response<SearchResultResponse>

        Mockito.`when`(apiService.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenReturn(resMock)
        Mockito.`when`(resMock.body()).thenReturn(mockSearchResult)

        runBlocking {
            val response = remote.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)
            Truth.assertThat(response?.size).isEqualTo(1)
            Truth.assertThat(response?.first()).isEqualTo(mockUserResponse)
        }
    }

    @Test
    fun searchUsers_errorIO() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<SearchResultResponse> = Mockito.mock(Call::class.java) as Call<SearchResultResponse>
        val resMock: Response<SearchResultResponse> = Mockito.mock(Response::class.java) as Response<SearchResultResponse>

        Mockito.`when`(apiService.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(IOException("Test Error Network"))
        Mockito.`when`(resMock.body()).thenReturn(mockSearchResult)

        runBlocking {
            try {
                val response = remote.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)
                throw RuntimeException("Wrong Behavior")
            } catch (e: IOException) {
                //expected to be here
            }
        }
    }

    @Test
    fun searchUsers_errorRuntime() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<SearchResultResponse> = Mockito.mock(Call::class.java) as Call<SearchResultResponse>
        val resMock: Response<SearchResultResponse> = Mockito.mock(Response::class.java) as Response<SearchResultResponse>

        Mockito.`when`(apiService.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(RuntimeException("Test Network Error"))
        Mockito.`when`(resMock.body()).thenReturn(mockSearchResult)

        runBlocking {
            try {
                val response = remote.searchUsers(keyword = "abc", cursor = 0, loadSize = 10)
                throw RuntimeException("Wrong Behavior")
            } catch (e: RuntimeException) {
                //expected to be here
            }
        }
    }

    @Test
    fun fetchUserByUsername_normal() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<UserResponse> = Mockito.mock(Call::class.java) as Call<UserResponse>
        val resMock: Response<UserResponse> = Mockito.mock(Response::class.java) as Response<UserResponse>

        Mockito.`when`(apiService.fetchUserByUsername(username = "abc")).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenReturn(resMock)
        Mockito.`when`(resMock.body()).thenReturn(mockUserResponse)

        runBlocking {
            val response = remote.fetchUserByUsername(username = "abc")
            Truth.assertThat(response).isEqualTo(mockUserResponse)
        }
    }

    @Test
    fun fetchUserByUsername_errorIO() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<UserResponse> = Mockito.mock(Call::class.java) as Call<UserResponse>
        val resMock: Response<UserResponse> = Mockito.mock(Response::class.java) as Response<UserResponse>

        Mockito.`when`(apiService.fetchUserByUsername(username = "abc")).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(IOException("Test Error Network"))
        Mockito.`when`(resMock.body()).thenReturn(mockUserResponse)

        runBlocking {
            try {
                val response = remote.fetchUserByUsername(username = "abc")
                throw RuntimeException("Wrong Behavior")
            } catch (e: IOException) {
                //expected to be here
            }
        }
    }

    @Test
    fun fetchUserByUsername_errorRuntime() {
        val remote = UserRemoteDataStore(apiService)

        val callMock: Call<UserResponse> = Mockito.mock(Call::class.java) as Call<UserResponse>
        val resMock: Response<UserResponse> = Mockito.mock(Response::class.java) as Response<UserResponse>

        Mockito.`when`(apiService.fetchUserByUsername(username = "abc")).thenReturn(callMock)
        Mockito.`when`(callMock.execute()).thenThrow(RuntimeException("Test Network Error"))
        Mockito.`when`(resMock.body()).thenReturn(mockUserResponse)

        runBlocking {
            try {
                val response = remote.fetchUserByUsername(username = "abc")
                throw RuntimeException("Wrong Behavior")
            } catch (e: RuntimeException) {
                //expected to be here
            }
        }
    }
}