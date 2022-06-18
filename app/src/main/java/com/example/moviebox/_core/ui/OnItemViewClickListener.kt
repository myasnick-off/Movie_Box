package com.example.moviebox._core.ui

import android.view.View
import com.example.moviebox._core.data.remote.model.MovieDTO

// интерфейс для обработки нажатий на элементы RecyclerView
interface OnItemViewClickListener {
    fun onItemClicked(movieId: Long)
    fun onItemLongClicked(movie: MovieDTO, view: View)
}