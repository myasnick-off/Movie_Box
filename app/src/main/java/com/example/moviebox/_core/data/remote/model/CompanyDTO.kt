package com.example.moviebox._core.data.remote.model

import com.google.gson.annotations.SerializedName

data class CompanyDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("origin_country")
    val originCountry: String
)
