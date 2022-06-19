package com.example.moviebox._core.data.local

import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.local.entities.FavoriteEntity
import com.example.moviebox._core.data.local.entities.HistoryEntity
import com.example.moviebox._core.data.local.entities.WishlistEntity
import com.example.moviebox._core.domain.LocalRepository

class LocalRepositoryImpl(private val db: ProfileDataBase) : LocalRepository {

    override fun getAllHistory(): List<MovieDTO> {
        return convertHistoryEntityToMovieDTO(db.historyDao().allHistory())
    }

    override fun saveEntityToHistory(movie: MovieDTO) {
        db.historyDao().insertInHistory(convertMovieDtoToHistoryEntity(movie))
    }

    override fun deleteEntityFromHistory(movie: MovieDTO) {
        db.historyDao().deleteInHistory(convertMovieDtoToHistoryEntity(movie))
    }

    override fun clearAllHistory() {
        db.historyDao().deleteAllHistory()
    }

    override fun getAllFavorite(): List<MovieDTO> {
        return convertFavoriteEntityToMovieDTO(db.favoriteDao().allFavorite())
    }

    override fun saveEntityToFavorite(movie: MovieDTO) {
        db.favoriteDao().insertInFavorite(convertMovieDtoToFavoriteEntity(movie))
    }

    override fun deleteEntityFromFavorite(movie: MovieDTO) {
        db.favoriteDao().deleteInFavorite(convertMovieDtoToFavoriteEntity(movie))
    }

    override fun clearAllFavorite() {
        db.favoriteDao().deleteAllFavorite()
    }

    override fun getAllWishList(): List<MovieDTO> {
        return convertWishlistEntityToMovieDTO(db.wishlistDao().allWishlist())
    }

    override fun saveEntityToWishList(movie: MovieDTO) {
        db.wishlistDao().insertInWishlist(convertMovieDtoToWishlistEntity(movie))
    }

    override fun deleteEntityFromWishList(movie: MovieDTO) {
        db.wishlistDao().deleteInWishlist(convertMovieDtoToWishlistEntity(movie))
    }

    override fun clearAllWishList() {
        db.wishlistDao().deleteAllWishlist()
    }

    override fun checkMovieInHistory(id: Long) =
        db.historyDao().getFromHistoryById(id).isNotEmpty()

    override fun checkMovieInFavorite(id: Long) =
        db.favoriteDao().getFromFavoriteById(id).isNotEmpty()

    override fun checkMovieInWishlist(id: Long) =
        db.wishlistDao().getFromWishlistById(id).isNotEmpty()

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