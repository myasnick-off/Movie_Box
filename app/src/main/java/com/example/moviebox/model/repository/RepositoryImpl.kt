package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Movie
import com.example.moviebox.model.entities.getDefaultMovie

class RepositoryImpl : Repository {

    override fun getMovieDataFromLocal(): Movie = getDefaultMovie()

    override fun getMovieListFromLocal() = listOf(
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie(),
        getDefaultMovie()
    )

    override fun getMovieDataFromServer(): Movie = getDefaultMovie()

    override fun getMovieListFromServer() = listOf(getDefaultMovie())
}