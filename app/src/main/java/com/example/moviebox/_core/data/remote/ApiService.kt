package com.example.moviebox._core.data.remote

import com.example.moviebox.BuildConfig
import com.example.moviebox._core.data.remote.model.GenreListDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import com.example.moviebox._core.data.remote.model.MovieListDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /** Запрос списка жанров */
    @GET("genre/movie/list")
    fun getGenreListAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG
    ): Deferred<Response<GenreListDTO>>
//  Пример url запроса:
//  https://api.themoviedb.org/3/genre/movie/list?api_key=<<api_key>>&language=en-US

    /** Запрос списка фильмов по жанрам */
    @GET("discover/movie")
    fun getMovieListByGenreAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG,
        @Query("sort_by") sortBy: String = ApiUtils.POPULARITY_SORT,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("with_genres") genreIds: Array<Int>
    ): Deferred<Response<MovieListDTO>>
//  Пример url запроса:
//  https://api.themoviedb.org/3/discover/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=en-US&sort_by=vote_average.desc&with_genres=10%2C20%2C30

    /** Запрос отфильтрованного списка фильмов */
    @GET("discover/movie")
    fun getMovieListByFilterAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = ApiUtils.LANG,
        @Query("sort_by") sortBy: String = ApiUtils.POPULARITY_SORT,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("release_date.gte") releaseDateGte: String,
        @Query("release_date.lte") releaseDateLte: String,
        @Query("vote_average.gte") voteAverageGte: Float,
        @Query("vote_average.lte") voteAverageLte: Float,
        @Query("with_genres") genreIds: ArrayList<Int>
    ): Deferred<Response<MovieListDTO>>
//  Пример url запроса:
//  https://api.themoviedb.org/3/discover/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=ru&sort_by=popularity.desc&include_adult=false&release_date.gte=1980&release_date.lte=2020&vote_average.gte=5.0&vote_average.lte=7.0&with_genres=80%2C99

    /** Запрос детализации фильма */
    @GET("movie/{id}")
    fun getMovieDetailsAsync(
        @Path(value = "id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") lang: String = ApiUtils.LANG
    ): Deferred<Response<MovieDetailsDTO>>
//  Пример url запроса:
//  https://api.themoviedb.org/3/movie/{id}?api_key={API_KEY}&language=ru

    /** Запрос на поиск фильма по названию */
    @GET("search/movie")
    fun getMovieListByPhraseAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = ApiUtils.LANG,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): Deferred<Response<MovieListDTO>>
//  Пример url запроса:
//  https://api.themoviedb.org/3/search/movie?api_key=02aa4f3b7db68ceb4c5c389d5dee7ce4&language=ru&query=history&include_adult=false

    companion object {
        private const val BASE_URL_MAIN_PART = "https://api.themoviedb.org/"
        private const val BASE_URL_VERSION = "3/"
        private const val BASE_URL = "${BASE_URL_MAIN_PART}${BASE_URL_VERSION}"

        fun getInstance(client: OkHttpClient): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}