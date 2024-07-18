package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entities.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY id DESC LIMIT 10")
    fun getAllHistory(): List<SearchHistoryEntity>

    @Query("SELECT * FROM search_history WHERE keyword = :keyword")
    fun getHistoryByKeyword(keyword: String): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE,)
    fun insert(vararg histories: SearchHistoryEntity)
}