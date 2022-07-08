package com.example.moviebox.home.domain

import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.home.domain.model.Category

class MovieListUseCase(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke(withAdult: Boolean): Result<List<Category>> {
        // получаем с сервера список жанров (id жанров и их названия)
        // и заполняем список списками фильмов по жанрам
        return remoteRepository.getGenreList().map { genreList ->
            val movieListByCategory = mutableListOf<Category>()
            for (genre in genreList.genres) {
                remoteRepository.getMovieListByGenre(withAdult = withAdult, genreId = genre.id)
                    .onSuccess { movieList ->
                        movieListByCategory.add(Category(genre.id, genre.name, movieList.results))
                    }
            }
            movieListByCategory
        }
    }
}