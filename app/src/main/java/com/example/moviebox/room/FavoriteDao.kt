package com.example.moviebox.room

import androidx.room.*
import com.example.moviebox.room.entities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteEntity")
    fun allFavorite(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE movieId = :movieId")
    fun getFromFavoriteById(movieId: Long): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInFavorite(entity: FavoriteEntity)

    @Update
    fun updateInFavorite(entity: FavoriteEntity)

    @Delete
    fun deleteInFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity")
    fun deleteAllFavorite()
}