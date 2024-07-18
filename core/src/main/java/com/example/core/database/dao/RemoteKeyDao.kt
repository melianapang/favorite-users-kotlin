package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    fun getRemoteKeyById(id: Int): RemoteKeyEntity?

    @Query("SELECT * FROM remote_key WHERE id = (SELECT MAX(id) FROM remote_key)")
    fun getLastRemoteKey(): RemoteKeyEntity?

    @Query("SELECT created_at FROM remote_key ORDER BY created_at DESC LIMIT 1")
    fun getLastUpdated(): Long

    @Query("DELETE FROM remote_key")
    fun clearRemoteKeys()
}