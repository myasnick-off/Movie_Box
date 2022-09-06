package com.example.moviebox.details.ui

import com.example.moviebox.details.domain.model.MovieDetails

sealed class DetailsViewState {
    data class Success(val movieData: MovieDetails) : DetailsViewState()
    data class Error(val error: Throwable) : DetailsViewState()
    object Loading : DetailsViewState()
    object Empty : DetailsViewState()
}