package com.example.moviebox._core.data.local.dao

import androidx.room.*
import com.example.moviebox._core.data.local.entities.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun allHistory(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE movieId = :movieId")
    fun getFromHistoryById(movieId: Long): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInHistory(entity: HistoryEntity)

    @Update
    fun updateInHistory(entity: HistoryEntity)

    @Delete
    fun deleteInHistory(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity")
    fun deleteAllHistory()
}