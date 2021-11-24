package com.example.moviebox.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebox.room.entities.FavoriteEntity
import com.example.moviebox.room.entities.HistoryEntity
import com.example.moviebox.room.entities.WishlistEntity

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