package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.entities.Movie

interface Repository {
    fun getMovieDataFromLocal(): Movie
    fun getMovieListFromLocal(): List<Movie>
    fun getCategoryListFromLocal(): List<Category>
    fun getMovieDataFromServer(): Movie
    fun getMovieListFromServer(): List<Movie>
    fun getCategoryListServer(): List<Category>
}