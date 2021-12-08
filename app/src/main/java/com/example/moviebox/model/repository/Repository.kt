package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.entities.FilterSet
import com.example.moviebox.model.rest_entities.GenreListDTO
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO

interface Repository {

    fun getMovieDataFromServer(id: Long): MovieDetailsDTO?
    fun getCategoryListFromServer(withAdult: Boolean): List<Category>?
    fun getGenreListFromServer(): GenreListDTO?
    fun searchByPhrase(phrase: String, withAdult: Boolean): List<MovieDTO>?
    fun filterSearch(filterSet: FilterSet, withAdult: Boolean): List<MovieDTO>?
}