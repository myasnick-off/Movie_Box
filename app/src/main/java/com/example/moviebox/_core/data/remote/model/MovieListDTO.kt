package com.example.moviebox._core.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieListDTO(

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: ArrayList<MovieDTO>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)