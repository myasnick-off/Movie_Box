package com.example.moviebox._core.domain.uscases

import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.RemoteRepository

class GetMovieListUseCase(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke(withAdult: Boolean): Result<List<MovieDTO>> {
        // получаем с сервера список жанров (id жанров и их названия)
        // и заполняем список списками фильмов по жанрам
        return remoteRepository.getGenreList().map { genreList ->
            val movieSet = mutableSetOf<MovieDTO>()
            for (genre in genreList.genres) {
                remoteRepository.getMovieListByGenre(withAdult = withAdult, genreId = genre.id)
                    .onSuccess { movies ->
                        movieSet += movies.results
                    }
            }
            movieSet.toList()
        }
    }
}