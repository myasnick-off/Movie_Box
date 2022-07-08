package com.example.moviebox._core.data.local

import com.example.moviebox._core.data.local.entities.FavoriteEntity
import com.example.moviebox._core.data.local.entities.HistoryEntity
import com.example.moviebox._core.data.local.entities.WishlistEntity
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryImpl(private val db: ProfileDataBase) : LocalRepository {

    override suspend fun getAllHistory(): List<MovieDTO> {
        return withContext(Dispatchers.IO) {
            convertHistoryEntityToMovieDTO(db.historyDao().allHistory())
        }
    }

    override suspend fun saveEntityToHistory(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.historyDao().insertInHistory(convertMovieDtoToHistoryEntity(movie))
        }
    }

    override suspend fun deleteEntityFromHistory(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.historyDao().deleteInHistory(convertMovieDtoToHistoryEntity(movie))
        }
    }

    override suspend fun clearAllHistory() {
        withContext(Dispatchers.IO) {
            db.historyDao().deleteAllHistory()
        }
    }

    override suspend fun getAllFavorite(): List<MovieDTO> {
        return withContext(Dispatchers.IO) {
            convertFavoriteEntityToMovieDTO(db.favoriteDao().allFavorite())
        }
    }

    override suspend fun saveEntityToFavorite(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.favoriteDao().insertInFavorite(convertMovieDtoToFavoriteEntity(movie))
        }
    }

    override suspend fun deleteEntityFromFavorite(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.favoriteDao().deleteInFavorite(convertMovieDtoToFavoriteEntity(movie))
        }
    }

    override suspend fun clearAllFavorite() {
        withContext(Dispatchers.IO) {
            db.favoriteDao().deleteAllFavorite()
        }
    }

    override suspend fun getAllWishList(): List<MovieDTO> {
        return withContext(Dispatchers.IO) {
            convertWishlistEntityToMovieDTO(db.wishlistDao().allWishlist())
        }
    }

    override suspend fun saveEntityToWishList(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.wishlistDao().insertInWishlist(convertMovieDtoToWishlistEntity(movie))
        }
    }

    override suspend fun deleteEntityFromWishList(movie: MovieDTO) {
        withContext(Dispatchers.IO) {
            db.wishlistDao().deleteInWishlist(convertMovieDtoToWishlistEntity(movie))
        }
    }

    override suspend fun clearAllWishList() {
        withContext(Dispatchers.IO) {
            db.wishlistDao().deleteAllWishlist()
        }
    }

    override suspend fun checkMovieInHistory(id: Long) =
        withContext(Dispatchers.IO) { db.historyDao().getFromHistoryById(id).isNotEmpty() }

    override suspend fun checkMovieInFavorite(id: Long) =
        withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) { db.favoriteDao().getFromFavoriteById(id).isNotEmpty() }
        }

    override suspend fun checkMovieInWishlist(id: Long) =
        withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) { db.wishlistDao().getFromWishlistById(id).isNotEmpty() }
        }

    private fun convertHistoryEntityToMovieDTO(entityList: List<HistoryEntity>): List<MovieDTO> {
        return entityList.map {
            MovieDTO(
                it.movieId,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.adult
            )
        }
    }

    private fun convertMovieDtoToHistoryEntity(movie: MovieDTO): HistoryEntity {
        return HistoryEntity(
            movie.id,
            movie.posterPath,
            movie.releaseDate,
            movie.title,
            movie.voteAverage,
            movie.adult
        )
    }

    private fun convertFavoriteEntityToMovieDTO(entityList: List<FavoriteEntity>): List<MovieDTO> {
        return entityList.map {
            MovieDTO(
                it.movieId,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.adult
            )
        }
    }

    private fun convertMovieDtoToFavoriteEntity(movie: MovieDTO): FavoriteEntity {
        return FavoriteEntity(
            movie.id,
            movie.posterPath,
            movie.releaseDate,
            movie.title,
            movie.voteAverage,
            movie.adult
        )
    }

    private fun convertWishlistEntityToMovieDTO(entityList: List<WishlistEntity>): List<MovieDTO> {
        return entityList.map {
            MovieDTO(
                it.movieId,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.adult
            )
        }
    }

    private fun convertMovieDtoToWishlistEntity(movie: MovieDTO): WishlistEntity {
        return WishlistEntity(
            movie.id,
            movie.posterPath,
            movie.releaseDate,
            movie.title,
            movie.voteAverage,
            movie.adult
        )
    }
}