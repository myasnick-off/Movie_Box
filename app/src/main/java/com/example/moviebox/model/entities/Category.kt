package com.example.moviebox.model.entities

data class Category(val name: CategoryName, var movieList: List<Movie>) {

    companion object {
        fun getDefaultCategoryList() = listOf(
            Category(CategoryName.LATEST, Movie.getDefaultMovieList(8, CategoryName.LATEST)),
            Category(CategoryName.THRILLER, Movie.getDefaultMovieList(8, CategoryName.THRILLER)),
            Category(CategoryName.COMEDY, Movie.getDefaultMovieList(8, CategoryName.COMEDY)),
        )
    }
}
