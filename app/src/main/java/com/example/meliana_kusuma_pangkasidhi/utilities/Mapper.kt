package com.example.meliana_kusuma_pangkasidhi.utilities

import com.example.core.api.model.UserResponse
import com.example.core.database.entities.UserEntity
import com.example.meliana_kusuma_pangkasidhi.model.User

fun List<UserEntity>.toUser() = this.map {
    User(
        id = it.id,
        username = it.username,
        avatarUrl = it.avatarUrl,
        isFavorite = it.isFavorite,
    )
}

fun UserResponse.toUser(isFavorite: Boolean) = User(
    id = this.id,
    username = this.login,
    name = this.name,
    avatarUrl = this.avatarUrl,
    bio = this.bio,
    company = this.company,
    email = this.email,
    location = this.location,
    followers = this.followers,
    following = this.following,
    publicRepos = this.publicRepos,
    isFavorite = isFavorite,
)