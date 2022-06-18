package com.example.moviebox._core.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterSet(
    var yearFrom: Int = 0,
    var yearTo: Int = 0,
    var ratingFrom: Float = 0f,
    var ratingTo: Float = 0f,
    var genres: ArrayList<Int> = arrayListOf()
): Parcelable
