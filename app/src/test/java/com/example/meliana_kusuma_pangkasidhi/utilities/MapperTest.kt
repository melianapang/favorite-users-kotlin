package com.example.meliana_kusuma_pangkasidhi.utilities

import com.example.core.api.model.UserResponse
import com.example.core.database.entities.UserEntity
import com.google.common.truth.Truth
import org.junit.Test

class MapperTest {
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
        isFavorite = false,
    )

    @Test
    fun mapUserResponseToUser() {
        val result = mockUserResponse.toUser(true)

        Truth.assertThat(result.id).isEqualTo(11)
        Truth.assertThat(result.name).isEqualTo("name")
        Truth.assertThat(result.company).isEqualTo("OP")
        Truth.assertThat(result.username).isEqualTo("taylor")
        Truth.assertThat(result.avatarUrl).isEqualTo("https://www.abc.com")
        Truth.assertThat(result.email).isEqualTo("tay@mail.com")
        Truth.assertThat(result.bio).isEqualTo("bio")
        Truth.assertThat(result.publicRepos).isEqualTo(6)
        Truth.assertThat(result.following).isEqualTo(1)
        Truth.assertThat(result.followers).isEqualTo(5)
        Truth.assertThat(result.isFavorite).isTrue()
    }

    @Test
    fun mapUserEntityToUser() {
        val result = listOf(mockUserEntity).toUser()

        Truth.assertThat(result.size).isEqualTo(1)
        Truth.assertThat(result.first().id).isEqualTo(mockUserEntity.id)
        Truth.assertThat(result.first().username).isEqualTo(mockUserEntity.username)
        Truth.assertThat(result.first().avatarUrl).isEqualTo(mockUserEntity.avatarUrl)
        Truth.assertThat(result.first().bio).isEmpty()
        Truth.assertThat(result.first().email).isEmpty()
        Truth.assertThat(result.first().name).isEmpty()
        Truth.assertThat(result.first().company).isEmpty()
        Truth.assertThat(result.first().publicRepos).isEqualTo(0)
        Truth.assertThat(result.first().followers).isEqualTo(0)
        Truth.assertThat(result.first().following).isEqualTo(0)
        Truth.assertThat(result.first().isFavorite).isFalse()
    }
}