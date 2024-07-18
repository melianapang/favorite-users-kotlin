package com.example.core.database.entities

import com.google.common.truth.Truth
import org.junit.Test

class UserEntityTest {
    @Test
    fun setGetUserEntity() {
        val user = UserEntity(
            id = 12,
            username = "username",
            avatarUrl = "https://www.abc.com",
            url = "https://www.abc.com",
            isFavorite = true
        )

        Truth.assertThat(user.id).isEqualTo(12)
        Truth.assertThat(user.username).isEqualTo("username")
        Truth.assertThat(user.avatarUrl).isEqualTo("https://www.abc.com")
        Truth.assertThat(user.url).isEqualTo("https://www.abc.com")
        Truth.assertThat(user.isFavorite).isTrue()
    }
}