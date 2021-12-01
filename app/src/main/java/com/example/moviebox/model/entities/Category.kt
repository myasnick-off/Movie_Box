package com.example.moviebox.model.entities

import android.os.Parcelable
import com.example.moviebox.model.rest_entities.MovieDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val id: Int, val name: String, var movieList: ArrayList<MovieDTO>) : Parcelable