package com.example.moviebox.model.rest_entities

import com.google.gson.annotations.SerializedName

data class MovieListDTO(
    val page: Int,
    val results: ArrayList<MovieDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)