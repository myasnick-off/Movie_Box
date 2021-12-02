package com.example.moviebox.model.rest

import com.example.moviebox.model.rest_entities.MovieListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchAPI {

    @GET("search/movie")
    fun getMovieListByPhrase(
        @Query("api_key") apiKey: String = ApiUtils.API_KEY,
        @Query("language") language: String = ApiUtils.LANG,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): Call<MovieListDTO>
}

//  Пример url запроса:
// https://api.themoviedb.org/3/search/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=ru&query=history&include_adult=false