package com.example.moviebox.ui.details

sealed class LocalDataAppState {
    data class Success(val hasMovie: List<Boolean>) : LocalDataAppState()
    data class Error(val error: Throwable) : LocalDataAppState()
    object Loading : LocalDataAppState()
}