package com.example.moviebox.ui.details

import com.example.moviebox.model.rest_entities.MovieDetailsDTO

sealed class DetailsAppState {
    data class Success(val movieData: MovieDetailsDTO) : DetailsAppState()
    data class Error(val error: Throwable) : DetailsAppState()
    object Loading : DetailsAppState()
}