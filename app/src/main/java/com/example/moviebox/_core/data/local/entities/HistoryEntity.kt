package com.example.moviebox._core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey
    val movieId: Long,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val adult: Boolean
)
