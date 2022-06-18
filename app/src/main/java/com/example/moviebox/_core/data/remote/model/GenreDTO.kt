package com.example.moviebox._core.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDTO(
    val id: Int,
    val name: String
) : Parcelable