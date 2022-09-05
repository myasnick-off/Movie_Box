package com.example.moviebox.details.domain

import com.example.moviebox._core.data.local.entities.MovieEntity
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO

class MovieEntityMapper {
    operator fun invoke(movieDTO: MovieDetailsDTO): MovieEntity {
        return MovieEntity(
            movieId = movieDTO.id,
            mainGenreId = movieDTO.genres.first().id,
            posterPath = movieDTO.posterPath,
            releaseDate = movieDTO.releaseDate,
            title = movieDTO.title,
            voteAverage = movieDTO.voteAverage,
            popularity = movieDTO.popularity,
            adult = movieDTO.adult
        )
    }
}