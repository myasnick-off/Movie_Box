package com.example.moviebox._core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebox._core.data.local.dao.FavoriteDao
import com.example.moviebox._core.data.local.dao.HistoryDao
import com.example.moviebox._core.data.local.dao.WishlistDao
import com.example.moviebox._core.data.local.entities.FavoriteEntity
import com.example.moviebox._core.data.local.entities.HistoryEntity
import com.example.moviebox._core.data.local.entities.WishlistEntity

@Database(
    entities = [HistoryEntity::class, FavoriteEntity::class, WishlistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProfileDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun wishlistDao(): WishlistDao
}