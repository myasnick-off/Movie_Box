package com.example.moviebox.details.ui

sealed class LocalDataAppState {
    data class Success(val hasMovie: List<Boolean>) : LocalDataAppState()
    data class Error(val error: Throwable) : LocalDataAppState()
    object Loading : LocalDataAppState()
}