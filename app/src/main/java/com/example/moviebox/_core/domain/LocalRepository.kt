package com.example.moviebox._core.domain

import com.example.moviebox._core.data.local.entities.MovieEntity
import com.example.moviebox._core.data.remote.model.MovieDTO

interface LocalRepository {
    suspend fun getAllLocalData(movieId: Long): Result<List<MovieEntity>>

    suspend fun saveEntityToLocal(movie: MovieEntity): Result<Boolean>

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