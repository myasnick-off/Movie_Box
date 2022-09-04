package com.example.moviebox.details.domain

import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.details.domain.model.MovieDetails

class GetMovieDetailsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
//    private val entityMapper: MovieEntityMapper,
    private val mapper: MovieDetailsMapper
) {

    suspend operator fun invoke(movieId: Long): Result<MovieDetails> {
        var inHistory = false
        var inFavorite = false
        var inWishlist = false
        localRepository.getAllLocalData(movieId = movieId)
            .onSuccess { movieFromLocal ->
//                inHistory = movieFromLocal.inHistory
//                inFavorite = movieFromLocal.inFavorite
//                inWishlist = movieFromLocal.inWishlist
            }
        return remoteRepository.getMovieData(movieId = movieId).mapCatching { movieFromRemote ->
//            if (!inHistory) localRepository.saveEntityToLocal(movie = )
            mapper(movie = movieFromRemote, inFavorite = inFavorite, inWishlist = inWishlist)
        }
    }
}