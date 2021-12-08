package com.example.moviebox.model.rest

import com.example.moviebox.model.rest_entities.MovieListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieFilterAPI {

    @GET("discover/movie")
    fun getMovieListByFilter(
        @Query("api_key") apiKey: String = ApiUtils.API_KEY,
        @Query("language") language: String = ApiUtils.LANG,
        @Query("sort_by") sortBy: String = ApiUtils.POPULARITY_SORT,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("release_date.gte") releaseDateGte: String,
        @Query("release_date.lte") releaseDateLte: String,
        @Query("vote_average.gte") voteAverageGte: Float,
        @Query("vote_average.lte") voteAverageLte: Float,
        @Query("with_genres") genreIds: ArrayList<Int>
    ): Call<MovieListDTO>
}


//  Пример url запроса:
// https://api.themoviedb.org/3/discover/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=ru&sort_by=popularity.desc&include_adult=false&release_date.gte=1980&release_date.lte=2020&vote_average.gte=5.0&vote_average.lte=7.0&with_genres=80%2C99