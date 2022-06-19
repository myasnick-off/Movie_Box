package com.example.moviebox._core.ui.model

import com.example.moviebox.home.domain.model.Category

sealed class AppState {
    data class Success(val categoryData: List<Category>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}