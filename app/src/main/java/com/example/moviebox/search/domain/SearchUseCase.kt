package com.example.moviebox.search.domain

import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.domain.RemoteRepository

class SearchUseCase(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke(phrase: String, withAdult: Boolean): Result<MovieListDTO> {
        return remoteRepository.searchByPhrase(phrase = phrase, withAdult = withAdult)
    }
}