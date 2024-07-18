package com.example.core.utilities

import com.example.core.api.model.UserResponse
import com.example.core.database.entities.RemoteKeyEntity
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

    val remoteKeyEntity = RemoteKeyEntity(
        id = 11,
        nextKey = 12,
        prevKey = 10,
    )

    @Test
    fun extractUserDetailResponseToRemoteKeyEntity_list() {
        val result = mockUserResponse.extractRemoteKeyEntity()
        Truth.assertThat(result.id).isEqualTo(remoteKeyEntity.id)
        Truth.assertThat(result.nextKey).isEqualTo(remoteKeyEntity.nextKey)
        Truth.assertThat(result.prevKey).isEqualTo(remoteKeyEntity.prevKey)
    }

    @Test
    fun mapUserResponseToUserEntity() {
        val result = mockUserResponse.toUserEntity(false)
        Truth.assertThat(result).isEqualTo(mockUserEntity)
    }

    @Test
    fun mapUserResponseToUserEntity_list() {
        val result = listOf(mockUserResponse).toUserEntity()
        Truth.assertThat(result).isEqualTo(listOf(mockUserEntity))
    }
}