package com.example.moviebox.details.domain

import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.details.domain.model.MovieDetails

class GetMovieDetailsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val entityMapper: MovieEntityMapper,
    private val mapper: MovieDetailsMapper
) {

    suspend operator fun invoke(movieId: Long): Result<MovieDetails> {
//        localRepository.getAllLocalData(movieId = movieId)
//            .onSuccess { movieFromLocal ->
//            }
        return remoteRepository.getMovieData(movieId = movieId).mapCatching { movieFromRemote ->
//            localRepository.saveEntityToLocal(movie = entityMapper(movieFromRemote))
            mapper(movie = movieFromRemote, inFavorite = false, inWishlist = false)
        }
    }
}