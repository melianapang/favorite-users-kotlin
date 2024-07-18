package com.example.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM users WHERE is_favorite = 1")
    fun getAllFavoriteUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: UserEntity)

    @Query("Update users SET is_favorite = :isFavorite WHERE id = :id")
    fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM users")
    fun clear()
}