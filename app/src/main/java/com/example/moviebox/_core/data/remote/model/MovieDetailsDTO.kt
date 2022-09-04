package com.example.moviebox._core.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(

    @SerializedName("id")
    val id: Long,

    @SerializedName("genres")
    val genres: List<GenreDTO>,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("production_countries")
    val countries: List<CountryDTO>,

    @SerializedName("production_companies")
    val companies: List<CompanyDTO>,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("adult")
    val adult: Boolean
)


