package com.example.moviebox.details.domain

import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox.details.domain.model.MovieDetails
import com.example.moviebox.details.domain.model.MovieStatus

class MovieDetailsMapper {
    operator fun invoke(movie: MovieDetailsDTO, inFavorite: Boolean, inWishlist: Boolean): MovieDetails {
        return MovieDetails(
            id = movie.id,
            genres = movie.genres.map { it.name },
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            title = movie.title,
            voteAverage = movie.voteAverage,
            popularity = movie.popularity,
            countries = movie.countries.map { it.name },
            companies = movie.companies.map { it.name },
            runtime = movie.runtime,
            budget = movie.budget,
            status = MovieStatus.findOrDefault(movie.status),
            adult = movie.adult,
            inFavorite = inFavorite,
            inWishlist = inWishlist
        )
    }
}