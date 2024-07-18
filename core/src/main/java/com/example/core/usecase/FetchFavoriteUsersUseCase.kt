package com.example.core.usecase

import com.example.core.database.entities.UserEntity
import com.example.core.repository.user.UserRepository
import javax.inject.Inject

class FetchFavoriteUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun getFavoriteUsers(): List<UserEntity> {
        return userRepository.getFavoriteUsers()
    }

    suspend fun isFavoriteUser(id: Int): Boolean {
        return userRepository.isFavoriteUser(id)
    }

    suspend fun updateFavorite(id: Int, isFavorite: Boolean) {
        userRepository.updateUserFavoriteStatus(id, isFavorite)
    }
}