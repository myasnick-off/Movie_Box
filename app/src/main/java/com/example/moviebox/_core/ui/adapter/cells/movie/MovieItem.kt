package com.example.moviebox._core.ui.adapter.cells.movie

import com.example.moviebox._core.ui.adapter.RecyclerItem

data class MovieItem(
    override val id: Long,
    val genreIds: List<Int>,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val popularity: Double,
    val adult: Boolean,
    val inHistory: Boolean,
    val inFavorite: Boolean,
    val inWishlist: Boolean
) : RecyclerItem