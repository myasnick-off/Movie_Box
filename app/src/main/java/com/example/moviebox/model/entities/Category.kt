package com.example.moviebox.model.entities

import com.example.moviebox.model.rest_entities.MovieDTO

data class Category(val id: Int, val name: String, var movieList: ArrayList<MovieDTO>) {

    companion object {
        fun getDefaultCategoryList() = listOf(
            Category(
                0,
                CategoryName.LATEST.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.LATEST)
            ),
            Category(
                1,
                CategoryName.THRILLER.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.THRILLER)
            ),
            Category(
                2,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                3,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                4,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                5,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                6,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                7,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                8,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                9,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                10,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
            Category(
                11,
                CategoryName.COMEDY.title,
                MovieDTO.getDefaultMovieList(20, CategoryName.COMEDY)
            ),
        )
    }
}
