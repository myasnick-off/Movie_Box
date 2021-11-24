package com.example.moviebox.model.entities

import com.example.moviebox.model.rest_entities.MovieDTO

data class Category(val id: Int, val name: String, var movieList: ArrayList<MovieDTO>)