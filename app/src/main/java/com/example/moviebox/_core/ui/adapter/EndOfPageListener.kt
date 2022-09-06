package com.example.moviebox._core.ui.adapter

interface EndOfPageListener {
    fun positionCheck(position: Int, count: Int): Boolean
    fun loadMoreCallback()

}