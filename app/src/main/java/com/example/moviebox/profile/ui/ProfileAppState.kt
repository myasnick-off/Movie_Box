package com.example.moviebox.profile.ui

import com.example.moviebox._core.data.remote.model.MovieDTO

sealed class ProfileAppState {
    data class Success(val movieList: List<MovieDTO>) : ProfileAppState()
    data class Error(val error: Throwable) : ProfileAppState()
    object Loading : ProfileAppState()
}