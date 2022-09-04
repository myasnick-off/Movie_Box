package com.example.moviebox.home.ui

import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem
import com.example.moviebox._core.ui.adapter.cells.category.CategoryListItem
import com.example.moviebox.utils.Genre

class CategoryListMapper {
    operator fun invoke(movieList: List<MovieItem>): List<CategoryListItem> {
        val categoryList = mutableListOf<CategoryListItem>()
        for (genre in Genre.values()) {
            categoryList.add(
                CategoryListItem(
                    categoryResId = genre.nameResId,
                    movies = movieList.filter { it.genreIds.contains(genre.id) }
                )
            )
        }
        return categoryList
    }
}