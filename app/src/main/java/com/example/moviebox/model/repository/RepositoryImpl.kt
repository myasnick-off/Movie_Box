package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.rest.GenreListRepo
import com.example.moviebox.model.rest.MovieDetailsRepo
import com.example.moviebox.model.rest.MovieListRepo
import com.example.moviebox.model.rest_entities.GenreListDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO

class RepositoryImpl : Repository {

    override fun getMovieDataFromServer(id: Int): MovieDetailsDTO? {
        return MovieDetailsRepo.api.getMovieDetails(id).execute().body()
    }

    override fun getCategoryListFromServer(): List<Category>? {
        // создаем пустой массив со списками фильмов по жанрам
        val categoryList = arrayListOf<Category>()

        // получаем с сервера список жанров (id жанров и их названия)
        val genreList = GenreListRepo.api.getGenreList().execute().body()

        // заполяем массив со списками фильмов по жанрам в соответсвии со списком жанров, полученном с сервера
        genreList?.let {
            for (i in 0 until it.genres.size) {
                val genre = it.genres[i]
                val movieList =
                    MovieListRepo.api.getMovieListByGenre(genreIds = arrayOf(genre.id))
                        .execute()
                        .body()
                movieList?.let { categoryList.add(Category(genre.id, genre.name, movieList.results)) }
            }
            if (categoryList.isNotEmpty()) return categoryList
        }
        return null
    }

    override fun getGenreListFromServer(): GenreListDTO? {
        return GenreListRepo.api.getGenreList().execute().body()
    }
}