package com.example.moviebox._core.domain.mapper

import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem

class DtoToUiMapper {
    operator fun invoke(entityList: List<MovieDTO>): List<MovieItem> {
        return entityList.map { mapDTO(it) }
    }

    private fun mapDTO(movieDto: MovieDTO): MovieItem {
        return MovieItem(
            id = movieDto.id,
            genreIds = movieDto.genreIds,
            posterPath = movieDto.posterPath,
            releaseDate = movieDto.releaseDate,
            title = movieDto.title,
            voteAverage = movieDto.voteAverage,
            popularity = movieDto.popularity,
            adult = movieDto.adult,
            inHistory = false,
            inFavorite = false,
            inWishlist = false
        )
    }
}