package com.example.moviebox._core.data.remote.model

import com.google.gson.annotations.SerializedName

data class CountryDTO(
    @SerializedName("iso_3166_1")
    val iso: String,

    @SerializedName("name")
    val name: String
)
