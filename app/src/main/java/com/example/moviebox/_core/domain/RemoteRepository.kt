package com.example.moviebox._core.domain

import com.example.moviebox._core.ui.model.Category
import com.example.moviebox._core.ui.model.FilterSet
import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO

interface RemoteRepository {

    fun getMovieData(id: Long): MovieDetailsDTO?
    fun getCategoryList(withAdult: Boolean): List<Category>?
    fun getGenreList(): GenreListDTO?
    fun searchByPhrase(phrase: String, withAdult: Boolean): List<MovieDTO>?
    fun filterSearch(filterSet: FilterSet, withAdult: Boolean): List<MovieDTO>?
}