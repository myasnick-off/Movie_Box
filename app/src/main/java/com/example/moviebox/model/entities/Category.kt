package com.example.moviebox.model.entities

data class Category(
    val name: String = "Latest",
    val movie: Movie = getDefaultMovie()
)

fun getDefaultMovie() = Movie(
    "Terminator",
    "http://media.filmz.ru/photos/full/filmz.ru_f_257418.jpg",
    8.2,
    "")
