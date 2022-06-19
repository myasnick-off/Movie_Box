package com.example.moviebox._core.domain

import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.ui.model.FilterSet

interface RemoteRepository {

    fun getMovieData(id: Long): MovieDetailsDTO?
    fun getMovieListByGenre(withAdult: Boolean, genreId: Int): MovieListDTO?
    fun getGenreList(): GenreListDTO?
    fun searchByPhrase(phrase: String, withAdult: Boolean): List<MovieDTO>?
    fun filterSearch(filterSet: FilterSet, withAdult: Boolean): List<MovieDTO>?
}