package com.example.moviebox.ui.profile

import com.example.moviebox.model.rest_entities.MovieDTO

sealed class ProfileAppState {
    data class Success(val movieList: List<MovieDTO>) : ProfileAppState()
    data class Error(val error: Throwable) : ProfileAppState()
    object Loading : ProfileAppState()
}