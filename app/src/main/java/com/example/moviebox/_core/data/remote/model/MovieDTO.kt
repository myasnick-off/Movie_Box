package com.example.moviebox._core.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(

    @SerializedName("id")
    val id: Long,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("adult")
    val adult: Boolean
) : Parcelable
