package com.example.moviebox._core.data.local.dao

import androidx.room.*
import com.example.moviebox._core.data.local.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllData(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE movieId = :movieId")
    fun getMovieById(movieId: Long): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: MovieEntity)

    @Update
    fun updateData(entity: MovieEntity)

    @Delete
    fun deleteMovie(entity: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    fun deleteAllData()
}