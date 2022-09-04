package com.example.moviebox._core.ui.adapter.cells.goto

import com.example.moviebox._core.ui.adapter.RecyclerItem

data class GotoItem(
    override val id: Long = 0L,
    val categoryId: Int
    ) : RecyclerItem