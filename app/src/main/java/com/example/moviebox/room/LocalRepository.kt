package com.example.moviebox.room

import com.example.moviebox.model.rest_entities.MovieDTO

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