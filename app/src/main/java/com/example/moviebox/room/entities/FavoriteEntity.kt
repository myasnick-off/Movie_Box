package com.example.moviebox.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey
    val movieId: Long,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val adult: Boolean
)
