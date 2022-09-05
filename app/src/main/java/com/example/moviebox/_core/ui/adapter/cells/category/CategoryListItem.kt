package com.example.moviebox._core.ui.adapter.cells.category

import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox.utils.Genre

data class CategoryListItem(
    override val id: Long = 0,
    val category: Genre,
    val movies: List<RecyclerItem>
) : RecyclerItem