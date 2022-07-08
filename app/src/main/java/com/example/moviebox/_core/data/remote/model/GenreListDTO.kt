package com.example.moviebox._core.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreListDTO(

    @SerializedName("genres")
    val genres: ArrayList<GenreDTO>
)
