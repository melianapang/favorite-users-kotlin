package com.example.core.api.model

import com.google.common.truth.Truth
import org.junit.Test

class SearchResultResponseTest {
    @Test
    fun setGetSearchResultResponseTest() {
        val items = arrayListOf(
            UserResponse(
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
        )
        val model = SearchResultResponse(
            totalCount = 10,
            isIncompleteResult = false,
            items = items,
        )

        Truth.assertThat(model.totalCount).isEqualTo(10)
        Truth.assertThat(model.isIncompleteResult).isFalse()
        Truth.assertThat(model.items).isEqualTo(items)
    }
}