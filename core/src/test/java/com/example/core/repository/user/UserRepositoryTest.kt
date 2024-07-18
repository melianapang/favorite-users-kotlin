package com.example.core.repository.user

import com.example.core.api.model.UserResponse
import com.example.core.database.dao.UserDao
import com.example.core.database.entities.UserEntity
import com.example.core.datastore.UserRemoteDataStore
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var remoteDataStore: UserRemoteDataStore

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

    private val mockUserEntity = UserEntity(
        id = 11,
        username = "taylor",
        avatarUrl = "https://www.abc.com",
        url = "https://www.abc.com",
        isFavorite = true,
    )

    @Before
    fun initTest() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun fetchUsers() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.fetchUsers(cursor = 0)
            Mockito.verify(remoteDataStore, Mockito.times(1)).fetchUsers(0)
        }
    }

    @Test
    fun searchUsers() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.searchUsers("taylor", 0, 10)
            Mockito.verify(remoteDataStore, Mockito.times(1)).searchUsers("taylor", 0, 10)
        }
    }

    @Test
    fun getUsers() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.getUsers()
            Mockito.verify(userDao, Mockito.times(1)).getAllUsers()
        }
    }

    @Test
    fun getFavoriteUsers() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.getFavoriteUsers()
            Mockito.verify(userDao, Mockito.times(1)).getAllFavoriteUsers()
        }
    }

    @Test
    fun fetchAndUpdateSavedUserByUsername() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            Mockito.`when`(userDao.getUserByUsername(mockUserEntity.username)).thenReturn(mockUserEntity)
            Mockito.`when`(remoteDataStore.fetchUserByUsername(mockUserEntity.username)).thenReturn(mockUserResponse)

            val res = repo.fetchAndUpdateSavedUserByUsername(mockUserEntity.username)

            Truth.assertThat(res?.id).isEqualTo(mockUserEntity.id)
            Truth.assertThat(res?.login).isEqualTo(mockUserEntity.username)

            Mockito.verify(userDao, Mockito.times(1)).getUserByUsername(mockUserEntity.username)
            Mockito.verify(remoteDataStore, Mockito.times(1)).fetchUserByUsername(mockUserEntity.username)
            Mockito.verify(userDao, Mockito.times(1)).insert(mockUserEntity)
        }
    }

    @Test
    fun fetchAndUpdateSavedUserByUsername_noRemoteData() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            Mockito.`when`(userDao.getUserByUsername(mockUserEntity.username)).thenReturn(mockUserEntity)
            Mockito.`when`(remoteDataStore.fetchUserByUsername(mockUserEntity.username)).thenReturn(null)

            val res = repo.fetchAndUpdateSavedUserByUsername(mockUserEntity.username)

            Truth.assertThat(res?.id).isNull()
            Truth.assertThat(res?.login).isNull()

            Mockito.verify(userDao, Mockito.times(1)).getUserByUsername(mockUserEntity.username)
            Mockito.verify(remoteDataStore, Mockito.times(1)).fetchUserByUsername(mockUserEntity.username)
            Mockito.verify(userDao, Mockito.times(0)).insert(mockUserEntity)
        }
    }

    @Test
    fun isFavoriteUser() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            Mockito.`when`(userDao.getUserById(mockUserEntity.id)).thenReturn(mockUserEntity)

            val res = repo.isFavoriteUser(mockUserEntity.id)

            Truth.assertThat(res).isTrue()
            Mockito.verify(userDao, Mockito.times(1)).getUserById(mockUserEntity.id)
        }
    }

    @Test
    fun insertUsersAndLastRemoteKey() {
        val repo = UserRepository(userDao, remoteDataStore)
        val listUser = arrayListOf(mockUserEntity)
        runBlocking {
            val res = repo.insertUsers(listUser)
            Mockito.verify(userDao, Mockito.times(1)).insertAll(listUser)
        }
    }

    @Test
    fun updateUserFavoriteStatus() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.updateUserFavoriteStatus(11, true)
            Mockito.verify(userDao, Mockito.times(1)).updateFavoriteStatus(11, true)
        }
    }

    @Test
    fun clearUsers() {
        val repo = UserRepository(userDao, remoteDataStore)

        runBlocking {
            val res = repo.clearUsers()
            Mockito.verify(userDao, Mockito.times(1)).clear()
        }
    }
}