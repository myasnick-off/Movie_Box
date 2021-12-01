package com.example.moviebox.ui

import android.view.View
import com.example.moviebox.model.rest_entities.MovieDTO

// интерфейс для обработки нажатий на элементы RecyclerView
interface OnItemViewClickListener {
    fun onItemClicked(movieId: Long)
    fun onItemLongClicked(movie: MovieDTO, view: View)
}