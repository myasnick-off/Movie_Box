package com.example.moviebox.model.entities.repository

import com.example.moviebox.model.entities.Movie
import com.example.moviebox.model.entities.getDefaultMovie

class RepositoryImpl: Repository {

    override fun getMovieDataFromLocal(): Movie = getDefaultMovie()

    override fun getMovieDataFromServer(): Movie = getDefaultMovie()
}