package com.example.moviebox.details.domain

import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.domain.RemoteRepository

class MovieDetailsUseCase(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke(movieId: Long): Result<MovieDetailsDTO> {
        return remoteRepository.getMovieData(id = movieId)
    }
}