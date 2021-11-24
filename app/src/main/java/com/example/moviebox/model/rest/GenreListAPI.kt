package com.example.moviebox.model.rest


import com.example.moviebox.model.rest_entities.GenreListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreListAPI {

    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String = ApiUtils.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG
    ): Call<GenreListDTO>
}

//  Пример url запроса:
//  https://api.themoviedb.org/3/genre/movie/list?api_key=<<api_key>>&language=en-US