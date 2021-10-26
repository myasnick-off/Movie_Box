package com.example.moviebox

import com.example.moviebox.model.entities.Category

sealed class AppState {
    data class Success(val categoryData: List<Category>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}