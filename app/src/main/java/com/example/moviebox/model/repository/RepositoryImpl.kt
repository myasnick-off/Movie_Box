package com.example.moviebox.model.repository

import com.example.moviebox.model.MovieLoader
import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.rest_entities.GenreListDTO
import com.example.moviebox.model.rest_entities.MovieDetailsDTO

class RepositoryImpl : Repository {

    override fun getCategoryListFromLocal() = Category.getDefaultCategoryList()

    override fun getMovieDataFromLocal() = MovieDetailsDTO.getDefaultDetails()

    override fun getMovieDataFromServer(id: Int): MovieDetailsDTO? {
        return MovieLoader.loadMovieDetails(id)
    }

    override fun getCategoryListFromServer(): List<Category>? {
        // создаем пустой массив со списками фильмов по жанрам
        val categoryList = arrayListOf<Category>()
        // получаем с сервера список жанров (id жанров и их названия)
        val genreList = MovieLoader.loadGenreList()

        // заполяем массив со списками фильмов по жанрам в соответсвии со списком жанров, полученном с сервера
        genreList?.let {
            for (i in 0 until genreList.genres.size) {
                val genre = genreList.genres[i]
                val movieList = MovieLoader.loadMovieListByGenre(intArrayOf(genre.id))
                movieList?.let { categoryList.add(Category(genre.name, movieList.results)) }
            }
            if (categoryList.isNotEmpty()) return categoryList
        }
        return null
    }

    override fun getGenreListFromServer(): GenreListDTO? {
        return MovieLoader.loadGenreList()
    }
}