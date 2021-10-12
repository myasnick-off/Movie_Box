package com.example.moviebox.model.repository

import com.example.moviebox.model.entities.*

class RepositoryImpl : Repository {

    override fun getMovieDataFromLocal(): Movie = getDefaultLatest()

    override fun getMovieListFromLocal() = listOf(
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest(),
        getDefaultLatest()
    )

    override fun getCategoryListFromLocal() = listOf(
        Category(CategoryName.LATEST, listOf(
            getDefaultLatest(), getDefaultLatest(), getDefaultLatest(), getDefaultLatest(),
            getDefaultLatest(), getDefaultLatest(), getDefaultLatest(), getDefaultLatest()
        )),

        Category(CategoryName.THRILLER, listOf(
            getDefaultThriller(), getDefaultThriller(), getDefaultThriller(), getDefaultThriller(),
            getDefaultThriller(), getDefaultThriller(), getDefaultThriller(), getDefaultThriller()
        )),

        Category(CategoryName.COMEDY, listOf(
            getDefaultComedy(), getDefaultComedy(), getDefaultComedy(), getDefaultComedy(),
            getDefaultComedy(), getDefaultComedy(), getDefaultComedy(), getDefaultComedy()
        )),
    )

    override fun getMovieDataFromServer(): Movie = getDefaultLatest()

    override fun getMovieListFromServer() = listOf(getDefaultLatest())

    override fun getCategoryListServer() = listOf(
        Category(CategoryName.LATEST, listOf(
            getDefaultLatest(), getDefaultLatest(), getDefaultLatest(), getDefaultLatest(),
            getDefaultLatest(), getDefaultLatest(), getDefaultLatest(), getDefaultLatest()
        )))
}