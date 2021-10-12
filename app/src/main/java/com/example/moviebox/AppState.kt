package com.example.moviebox

import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.entities.Movie

sealed class AppState {
    data class Success(val categoryData: List<Category>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}