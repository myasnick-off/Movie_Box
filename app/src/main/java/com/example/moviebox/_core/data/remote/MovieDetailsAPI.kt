package com.example.moviebox._core.data.remote

import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsAPI {

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path(value = "id") movieId: Long,
        @Query("api_key") apiKey: String = ApiUtils.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG
    ): Call<MovieDetailsDTO>
}

//  Пример url запроса:
//  "https://api.themoviedb.org/3/movie/{id}?api_key={API_KEY}&language=ru"
