package com.example.moviebox.filter.ui.model

import com.example.moviebox._core.data.remote.model.GenreDTO

sealed class GenresAppState {
    data class Success(val genreData: List<GenreDTO>) : GenresAppState()
    data class Error(val error: Throwable) : GenresAppState()
    object Loading : GenresAppState()
}