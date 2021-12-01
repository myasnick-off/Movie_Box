package com.example.moviebox.room

import androidx.room.*
import com.example.moviebox.room.entities.WishlistEntity

@Dao
interface WishlistDao {

    @Query("SELECT * FROM WishlistEntity")
    fun allWishlist(): List<WishlistEntity>

    @Query("SELECT * FROM WishlistEntity WHERE movieId = :movieId")
    fun getFromWishlistById(movieId: Long): List<WishlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInWishlist(entity: WishlistEntity)

    @Update
    fun updateInWishlist(entity: WishlistEntity)

    @Delete
    fun deleteInWishlist(entity: WishlistEntity)

    @Query("DELETE FROM WishlistEntity")
    fun deleteAllWishlist()
}