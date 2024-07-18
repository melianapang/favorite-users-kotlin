package com.example.meliana_kusuma_pangkasidhi.model

data class User (
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val name: String? = "",
    val bio: String? = "",
    val company: String? = "",
    val email: String? = "",
    val location: String? = "",
    val following: Int = 0,
    val followers: Int = 0,
    val publicRepos: Int = 0,
    val isFavorite: Boolean
)