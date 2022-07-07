package com.example.moviebox._core.domain

import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.example.moviebox._core.ui.model.FilterSet
import retrofit2.Response

interface RemoteRepository {

    suspend fun getMovieData(id: Long): Result<MovieDetailsDTO>
    suspend fun getMovieListByGenre(withAdult: Boolean, genreId: Int): Result<MovieListDTO>
    suspend fun getGenreList(): Result<GenreListDTO>
    suspend fun searchByPhrase(phrase: String, withAdult: Boolean): Result<MovieListDTO>
    suspend fun filterSearch(filterSet: FilterSet, withAdult: Boolean): Result<MovieListDTO>
}