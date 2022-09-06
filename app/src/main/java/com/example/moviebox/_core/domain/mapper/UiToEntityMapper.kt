package com.example.moviebox._core.domain.mapper

import com.example.moviebox._core.data.local.entities.MovieEntity
import com.example.moviebox._core.ui.model.MovieItem

class UiToEntityMapper {

    fun mapItem(entity: MovieItem): MovieEntity {
        return MovieEntity(
            movieId = entity.id,
            mainGenreId = entity.genreIds.first(),
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            title = entity.title,
            voteAverage = entity.voteAverage,
            popularity = entity.popularity,
            adult = entity.adult,
            inHistory = entity.inHistory,
            inFavorite = entity.inFavorite,
            inWishlist = entity.inWishlist
        )
    }
}