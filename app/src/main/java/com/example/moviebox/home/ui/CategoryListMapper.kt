package com.example.moviebox.home.ui

import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox.home.ui.adapter.category.CategoryListItem
import com.example.moviebox.home.ui.adapter.goto.GotoItem
import com.example.moviebox._core.ui.model.MovieItem
import com.example.moviebox.utils.Genre

class CategoryListMapper {
    operator fun invoke(movieList: List<MovieItem>): List<RecyclerItem> {
        val categoryList = mutableListOf<RecyclerItem>()
        for (genre in Genre.values()) {
            categoryList.add(
                CategoryListItem(
                    category = genre,
                    movies = movieList.filter { it.genreIds.contains(genre.id) } + GotoItem(categoryId = genre.id)
                )
            )
        }
        return categoryList
    }
}