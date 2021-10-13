package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.*

class RepositoryImpl : Repository {

    override fun getMovieDataFromLocal(): Movie = Movie.getDefaultLatest()

    override fun getMovieListFromLocal() = Movie.getDefaultMovieList(8, CategoryName.LATEST)

    override fun getCategoryListFromLocal() = Category.getDefaultCategoryList()

    override fun getMovieDataFromServer() = Movie.getDefaultLatest()

    override fun getMovieListFromServer() = Movie.getDefaultMovieList(8, CategoryName.LATEST)

    override fun getCategoryListServer() = Category.getDefaultCategoryList()
}