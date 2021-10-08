package com.example.moviebox.model.entities.repository

import com.example.moviebox.model.entities.Movie

interface Repository {
    fun getMovieDataFromLocal(): Movie
    fun getMovieDataFromServer(): Movie
}