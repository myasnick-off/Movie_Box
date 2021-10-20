package com.example.moviebox.model.rest_entities

data class MovieListDTO(
    val page: Int,
    val results: ArrayList<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)