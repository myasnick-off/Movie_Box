package com.example.moviebox.model.rest

import com.example.moviebox.model.rest_entities.MovieListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListAPI {

    @GET("discover/movie")
    fun getMovieListByGenre(
        @Query("api_key") apiKey: String = ApiUtils.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG,
        @Query("sort_by") sortBy: String = ApiUtils.POPULARITY_SORT,
        @Query("with_genres") genreIds: Array<Int>
    ): Call<MovieListDTO>
}

//  Пример url запроса:
//  https://api.themoviedb.org/3/discover/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=en-US&sort_by=vote_average.desc&with_genres=10%2C20%2C30