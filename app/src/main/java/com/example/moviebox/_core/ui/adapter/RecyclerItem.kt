package com.example.moviebox._core.ui.adapter

interface RecyclerItem {
    val id: Long
    override fun equals(other: Any?): Boolean
}