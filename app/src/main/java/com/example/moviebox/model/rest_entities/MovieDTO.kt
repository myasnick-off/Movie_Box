package com.example.moviebox.model.rest_entities

import android.os.Parcelable
import com.example.moviebox.model.entities.CategoryName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(
    val id: Int,
    val release_date: String,
    val title: String,
    val vote_average: Number
) : Parcelable {

    companion object {

        private fun getDefaultLatest() = MovieDTO(
            1234,
            "10.09.2021",
            "Дюна",
            7.9
        )

        private fun getDefaultThriller() = MovieDTO(
            6234,
            "12.08.1984",
            "Терминатор",
            8.0
        )

        private fun getDefaultComedy() = MovieDTO(

            6334,
            "25.07.1998",
            "Карты, деньги, два ствола",
            8.6
        )

        fun getDefaultMovieList(size: Int, category: CategoryName): ArrayList<MovieDTO> {
            val movies = arrayListOf<MovieDTO>()
            for (i in 1..size) {
                when (category) {
                    CategoryName.LATEST -> movies.add(getDefaultLatest())
                    CategoryName.COMEDY -> movies.add(getDefaultComedy())
                    CategoryName.THRILLER -> movies.add(getDefaultThriller())
                    else -> movies.clear()
                }
            }
            return movies
        }
    }
}
