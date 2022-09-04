package com.example.moviebox.filter.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterSet(
    var yearFrom: Int = 0,
    var yearTo: Int = 2022,
    var ratingFrom: Float = 0f,
    var ratingTo: Float = 10f,
    var genres: ArrayList<Int> = arrayListOf()
): Parcelable
