package com.example.moviebox.details.domain

import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.details.domain.model.MovieDetails

class GetMovieDetailsUseCase(
    private val remoteRepository: RemoteRepository,
    private val mapper: MovieDetailsMapper
) {

    suspend operator fun invoke(movieId: Long): Result<MovieDetails> {
        return remoteRepository.getMovieData(movieId = movieId).mapCatching { movieFromRemote ->
            mapper(movie = movieFromRemote, inFavorite = false, inWishlist = false)
        }
    }
}