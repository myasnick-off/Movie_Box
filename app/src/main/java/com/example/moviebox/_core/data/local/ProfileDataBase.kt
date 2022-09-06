package com.example.moviebox._core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebox._core.data.local.dao.MovieDao
import com.example.moviebox._core.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class ProfileDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}