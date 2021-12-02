package com.example.moviebox.ui.filter

import com.example.moviebox.model.rest_entities.GenreDTO

sealed class GenresAppState {
    data class Success(val genreData: List<GenreDTO>) : GenresAppState()
    data class Error(val error: Throwable) : GenresAppState()
    object Loading : GenresAppState()
}