package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Movie

interface Repository {
    fun getMovieDataFromLocal(): Movie
    fun getMovieListFromLocal(): List<Movie>
    fun getMovieDataFromServer(): Movie
    fun getMovieListFromServer(): List<Movie>
}