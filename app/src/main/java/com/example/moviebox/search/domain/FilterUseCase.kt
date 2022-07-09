package com.example.moviebox.search.domain

import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.filter.ui.model.FilterSet

class FilterUseCase(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke(filterSet: FilterSet, withAdult: Boolean): Result<MovieListDTO> {
        return remoteRepository.filterSearch(filterSet = filterSet, withAdult = withAdult)
    }
}