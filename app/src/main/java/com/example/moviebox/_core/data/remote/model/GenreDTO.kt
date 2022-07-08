package com.example.moviebox._core.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
) : Parcelable