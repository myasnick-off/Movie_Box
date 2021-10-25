package com.example.moviebox.model.entities

import android.os.Parcelable
import com.example.moviebox.model.rest_entities.MovieDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val name: String, var movieList: ArrayList<MovieDTO>): Parcelable {

    companion object {
        fun getDefaultCategoryList() = listOf(
            Category(
                CategoryName.LATEST.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.LATEST)
            ),
            Category(
                CategoryName.THRILLER.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.THRILLER)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
        )
    }
}
