package com.example.core.api.model

import com.google.common.truth.Truth
import org.junit.Test

class UserResponseTest {
    @Test
    fun setGetUserResponseTest() {
        val user = UserResponse(
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

        Truth.assertThat(user.id).isEqualTo(1)
        Truth.assertThat(user.name).isEqualTo("name")
        Truth.assertThat(user.company).isEqualTo("OP")
        Truth.assertThat(user.login).isEqualTo("username")
        Truth.assertThat(user.avatarUrl).isEqualTo("https://www.abc.com")
        Truth.assertThat(user.url).isEqualTo("https://www.abc.com")
        Truth.assertThat(user.blog).isEqualTo("https://www.abc.com")
        Truth.assertThat(user.location).isEqualTo("Surabaya")
        Truth.assertThat(user.email).isEqualTo("tay@mail.com")
        Truth.assertThat(user.bio).isEqualTo("bio")
        Truth.assertThat(user.publicRepos).isEqualTo(6)
        Truth.assertThat(user.following).isEqualTo(1)
        Truth.assertThat(user.followers).isEqualTo(5)
    }
}