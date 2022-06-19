package com.example.moviebox._core.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsDTO(

    @SerializedName("id")
    val id: Long,

    @SerializedName("genres")
    val genres: ArrayList<GenreDTO>,

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

    @SerializedName("adult")
    val adult: Boolean
) : Parcelable


