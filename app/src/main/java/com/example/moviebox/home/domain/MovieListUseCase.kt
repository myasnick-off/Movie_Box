package com.example.moviebox.home.domain

import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox.home.domain.model.Category

class MovieListUseCase(private val remoteRepository: RemoteRepository) {

    operator fun invoke(withAdult: Boolean): List<Category>? {
        // создаем пустой массив со списками фильмов по жанрам
        val categoryList = mutableListOf<Category>()

        // получаем с сервера список жанров (id жанров и их названия)
        val genreList = remoteRepository.getGenreList()

        // заполяем массив со списками фильмов по жанрам в соответсвии со списком жанров, полученном с сервера
        genreList?.let {
            for (i in 0 until it.genres.size) {
                val genre = it.genres[i]
                val movieList =
                    remoteRepository.getMovieListByGenre(withAdult = withAdult, genreId = genre.id)
                movieList?.let {
                    categoryList.add(
                        Category(
                            genre.id,
                            genre.name,
                            movieList.results
                        )
                    )
                }
            }
            if (categoryList.isNotEmpty()) return categoryList
        }
        return null
    }
}