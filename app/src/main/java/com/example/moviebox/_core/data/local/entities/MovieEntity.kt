package com.example.moviebox._core.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val movieId: Long,
    @ColumnInfo(name = "main_genre_id") val mainGenreId: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "in_history") val inHistory: Boolean = false,
    @ColumnInfo(name = "in_favorite") val inFavorite: Boolean = false,
    @ColumnInfo(name = "in_wishlist") val inWishlist: Boolean = false
)
