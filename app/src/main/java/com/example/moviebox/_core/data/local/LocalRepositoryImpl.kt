package com.example.moviebox._core.data.local

import com.example.moviebox._core.data.local.entities.MovieEntity
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class LocalRepositoryImpl(private val db: ProfileDataBase) : LocalRepository {

    override suspend fun getAllLocalData(movieId: Long): Result<List<MovieEntity>> {
        return try {
            val data = withContext(Dispatchers.IO) {
                db.movieDao().getAllData()
            }
            Result.success(value = data)
        } catch (ex: IOException) {
            return Result.failure(exception = ex)
        }
    }

    override suspend fun saveEntityToLocal(movie: MovieEntity): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntityFromHistory(movie: MovieDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntityFromFavorite(movie: MovieDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntityFromWishList(movie: MovieDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllHistory() {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllFavorite() {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllWishList() {
        TODO("Not yet implemented")
    }

    override suspend fun checkMovieInHistory(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkMovieInFavorite(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkMovieInWishlist(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}