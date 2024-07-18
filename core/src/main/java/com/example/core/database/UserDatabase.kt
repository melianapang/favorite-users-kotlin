package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.dao.SearchHistoryDao
import com.example.core.database.dao.UserDao
import com.example.core.database.entities.RemoteKeyEntity
import com.example.core.database.entities.SearchHistoryEntity
import com.example.core.database.entities.UserEntity

@Database(entities = [UserEntity::class, RemoteKeyEntity::class, SearchHistoryEntity::class], exportSchema = false, version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun historySearchDao(): SearchHistoryDao
}