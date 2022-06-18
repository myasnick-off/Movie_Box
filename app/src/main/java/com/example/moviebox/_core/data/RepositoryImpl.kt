package com.example.moviebox._core.data

import com.example.moviebox._core.domain.Repository
import com.example.moviebox._core.ui.model.Category
import com.example.moviebox._core.ui.model.FilterSet
import com.example.moviebox._core.data.remote.*
import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO

class RepositoryImpl : Repository {

    override fun getMovieDataFromServer(id: Long): MovieDetailsDTO? {
        return MovieDetailsRepo.api.getMovieDetails(id).execute().body()
    }

    // метод запроса с сервера списков фильмов по жанрам
    // в качестве параметрва передаем статус допуска к фильмам для взрослых
    override fun getCategoryListFromServer(withAdult: Boolean): List<Category>? {
        // создаем пустой массив со списками фильмов по жанрам
        val categoryList = arrayListOf<Category>()

        // получаем с сервера список жанров (id жанров и их названия)
        val genreList = GenreListRepo.api.getGenreList().execute().body()

        // заполяем массив со списками фильмов по жанрам в соответсвии со списком жанров, полученном с сервера
        genreList?.let {
            for (i in 0 until it.genres.size) {
                val genre = it.genres[i]
                val movieList =
                    MovieListRepo.api
                        .getMovieListByGenre(includeAdult = withAdult, genreIds = arrayOf(genre.id))
                        .execute()
                        .body()
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

    override fun getGenreListFromServer(): GenreListDTO? {
        return GenreListRepo.api.getGenreList().execute().body()
    }

    override fun searchByPhrase(phrase: String, withAdult: Boolean): List<MovieDTO>? {
        return MovieSearchRepo.api
            .getMovieListByPhrase(query = phrase, includeAdult = withAdult)
            .execute()
            .body()?.results
    }

    override fun filterSearch(filterSet: FilterSet, withAdult: Boolean): List<MovieDTO>? {
        return MovieFilterRepo.api
            .getMovieListByFilter(
                includeAdult = withAdult,
                releaseDateGte = filterSet.yearFrom.toString(),
                releaseDateLte = filterSet.yearTo.toString(),
                voteAverageGte = filterSet.ratingFrom,
                voteAverageLte = filterSet.ratingTo,
                genreIds = filterSet.genres
            )
            .execute()
            .body()?.results
    }
}