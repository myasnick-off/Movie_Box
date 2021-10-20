package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.rest_entities.GenreListDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO

interface Repository {

    fun getCategoryListFromLocal(): List<Category>
    fun getMovieDataFromLocal(): MovieDetailsDTO

    fun getMovieDataFromServer(id: Int): MovieDetailsDTO?
    fun getCategoryListFromServer(): List<Category>?
    fun getGenreListFromServer(): GenreListDTO?
}