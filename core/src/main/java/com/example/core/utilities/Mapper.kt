package com.example.core.utilities

import com.example.core.api.model.UserResponse
import com.example.core.database.entities.RemoteKeyEntity
import com.example.core.database.entities.UserEntity

fun UserResponse.extractRemoteKeyEntity() = RemoteKeyEntity(
    id = this.id,
    nextKey = this.id + 1,
    prevKey = this.id - 1,
    createdAt = System.currentTimeMillis(),
)


fun List<UserResponse>.toUserEntity() = this.map {
    UserEntity(
        id = it.id,
        username = it.login,
        url = it.url,
        avatarUrl = it.avatarUrl,
    )
}

fun UserResponse.toUserEntity(isFavorite: Boolean) = UserEntity(
    id = this.id,
    username = this.login,
    url = this.url,
    avatarUrl = this.avatarUrl,
    isFavorite = isFavorite,
)