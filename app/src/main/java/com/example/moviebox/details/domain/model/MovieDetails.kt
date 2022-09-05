package com.example.moviebox.details.domain.model

data class MovieDetails(
        val id: Long,
        val genres: List<String>,
        val overview: String,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
        val voteAverage: Double,
        val popularity: Double,
        val countries: List<String>,
        val companies: List<String>,
        val runtime: Int?,
        val budget: Int,
        val status: MovieStatus,
        val adult: Boolean,
        val inHistory: Boolean = false,
        val inFavorite: Boolean = false,
        val inWishlist: Boolean = false
)
