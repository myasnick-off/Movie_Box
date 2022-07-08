package com.example.moviebox._core.domain

import com.example.moviebox._core.data.remote.model.MovieDTO

interface LocalRepository {
    suspend fun getAllHistory(): List<MovieDTO>
    suspend fun getAllFavorite(): List<MovieDTO>
    suspend fun getAllWishList(): List<MovieDTO>

    suspend fun saveEntityToHistory(movie: MovieDTO)
    suspend fun saveEntityToFavorite(movie: MovieDTO)
    suspend fun saveEntityToWishList(movie: MovieDTO)

    suspend fun deleteEntityFromHistory(movie: MovieDTO)
    suspend fun deleteEntityFromFavorite(movie: MovieDTO)
    suspend fun deleteEntityFromWishList(movie: MovieDTO)

    suspend fun clearAllHistory()
    suspend fun clearAllFavorite()
    suspend fun clearAllWishList()

    suspend fun checkMovieInHistory(id: Long): Boolean
    suspend fun checkMovieInFavorite(id: Long): Boolean
    suspend fun checkMovieInWishlist(id: Long): Boolean
}