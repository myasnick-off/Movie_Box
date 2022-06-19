package com.example.moviebox._core.domain

import com.example.moviebox._core.data.remote.model.MovieDTO

interface LocalRepository {
    fun getAllHistory(): List<MovieDTO>
    fun getAllFavorite(): List<MovieDTO>
    fun getAllWishList(): List<MovieDTO>

    fun saveEntityToHistory(movie: MovieDTO)
    fun saveEntityToFavorite(movie: MovieDTO)
    fun saveEntityToWishList(movie: MovieDTO)

    fun deleteEntityFromHistory(movie: MovieDTO)
    fun deleteEntityFromFavorite(movie: MovieDTO)
    fun deleteEntityFromWishList(movie: MovieDTO)

    fun clearAllHistory()
    fun clearAllFavorite()
    fun clearAllWishList()

    fun checkMovieInHistory(id: Long): Boolean
    fun checkMovieInFavorite(id: Long): Boolean
    fun checkMovieInWishlist(id: Long): Boolean
}