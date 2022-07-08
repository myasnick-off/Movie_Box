package com.example.moviebox.details.ui

import com.example.moviebox._core.data.remote.model.MovieDetailsDTO

sealed class DetailsAppState {
    data class Success(val movieData: MovieDetailsDTO) : DetailsAppState()
    data class Error(val error: Throwable) : DetailsAppState()
    object Loading : DetailsAppState()
}