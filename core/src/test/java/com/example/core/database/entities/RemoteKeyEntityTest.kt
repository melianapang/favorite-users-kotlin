package com.example.core.database.entities

import com.google.common.truth.Truth
import org.junit.Test

class RemoteKeyEntityTest {
    @Test
    fun setGetRemoteKeyEntity() {
        val remoteKey = RemoteKeyEntity(
            id = 12,
            prevKey = 11,
            nextKey = 13,
        )

        Truth.assertThat(remoteKey.id).isEqualTo(12)
        Truth.assertThat(remoteKey.prevKey).isEqualTo(11)
        Truth.assertThat(remoteKey.nextKey).isEqualTo(13)
    }
}