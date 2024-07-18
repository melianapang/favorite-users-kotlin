package com.example.core.repository.user

import androidx.paging.PagingSource
import com.example.core.api.model.UserResponse
import com.example.core.database.dao.UserDao
import com.example.core.database.entities.UserEntity
import com.example.core.datastore.UserRemoteDataStore
import com.example.core.utilities.toUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val remote: UserRemoteDataStore,
) {
    suspend fun fetchUsers(cursor: Int): List<UserResponse>? {
        return withContext(Dispatchers.IO) {
            remote.fetchUsers(cursor)
        }
    }

    suspend fun searchUsers(keyword: String, cursor: Int, loadSize: Int): List<UserResponse>? {
        return withContext(Dispatchers.IO) {
            remote.searchUsers(keyword, cursor, loadSize)
        }
    }

    fun getUsers(): PagingSource<Int, UserEntity> {
        return dao.getAllUsers()
    }

    suspend fun getFavoriteUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllFavoriteUsers()
        }
    }

    suspend fun fetchAndUpdateSavedUserByUsername(username: String): UserResponse? = withContext(Dispatchers.IO) {
        val savedUser = dao.getUserByUsername(username)
        val isFavoriteUser = savedUser?.isFavorite == true
        val user = remote.fetchUserByUsername(username)
        if (user != null) {
            val userEntity = user.toUserEntity(isFavoriteUser)
            dao.insert(userEntity)
        }
        user
    }

    suspend fun isFavoriteUser(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getUserById(id)?.isFavorite == true
        }
    }

    suspend fun insertUsers(users: List<UserEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(users)
        }
    }

    suspend fun updateUserFavoriteStatus(id: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateFavoriteStatus(id, isFavorite)
        }
    }

    suspend fun clearUsers() {
        withContext(Dispatchers.IO) {
            dao.clear()
        }
    }
}