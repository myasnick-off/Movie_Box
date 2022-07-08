package com.example.moviebox.home.domain.model

import android.os.Parcelable
import com.example.moviebox._core.data.remote.model.MovieDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    var movieList: ArrayList<MovieDTO>
    ) : Parcelable