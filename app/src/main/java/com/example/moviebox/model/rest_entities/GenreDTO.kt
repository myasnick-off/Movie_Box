package com.example.moviebox.model.rest_entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDTO(
    val id: Int,
    val name: String
): Parcelable