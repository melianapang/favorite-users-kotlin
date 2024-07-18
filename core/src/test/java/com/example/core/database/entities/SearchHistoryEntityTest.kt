package com.example.core.database.entities

import com.google.common.truth.Truth
import org.junit.Test

class SearchHistoryEntityTest {
    @Test
    fun setGetHistorySearchEntity() {
        val remoteKey = SearchHistoryEntity(
            id = 1,
            keyword = "taylor"
        )

        Truth.assertThat(remoteKey.id).isEqualTo(1)
        Truth.assertThat(remoteKey.keyword).isEqualTo("taylor")
    }
}