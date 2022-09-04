package com.example.moviebox._core.ui.adapter.cells.category

import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem

data class CategoryListItem(
    override val id: Long = 0,
    val categoryResId: Int,
    val movies: List<RecyclerItem>
) : RecyclerItem