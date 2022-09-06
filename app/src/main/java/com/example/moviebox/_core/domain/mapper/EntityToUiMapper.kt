package com.example.moviebox._core.domain.mapper

import com.example.moviebox._core.data.local.entities.MovieEntity
import com.example.moviebox._core.ui.model.MovieItem

class EntityToUiMapper {

    fun mapList(entityList: List<MovieEntity>): List<MovieItem> {
        return entityList.map { mapEntity(it) }
    }

    private fun mapEntity(entity: MovieEntity): MovieItem {
        return MovieItem(
            id = entity.movieId,
            genreIds = listOf(entity.mainGenreId),
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            title = entity.title,
            voteAverage = entity.voteAverage,
            popularity = entity.popularity,
            adult = entity.adult,
            inHistory = entity.inHistory,
            inFavorite = entity.inFavorite,
            inWishlist = entity.inWishlist,
        )
    }
}