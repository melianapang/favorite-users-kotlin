package com.example.meliana_kusuma_pangkasidhi.di

import android.content.Context
import androidx.room.Room
import com.example.core.database.UserDatabase
import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.dao.SearchHistoryDao
import com.example.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "users-db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeyDao(db: UserDatabase): RemoteKeyDao {
        return db.remoteKeyDao()
    }

    @Singleton
    @Provides
    fun provideHistorySearchDao(db: UserDatabase): SearchHistoryDao {
        return db.historySearchDao()
    }
}