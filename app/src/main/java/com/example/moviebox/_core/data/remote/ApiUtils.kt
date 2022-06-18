package com.example.moviebox._core.data.remote

import com.example.moviebox.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {

    private const val BASE_URL_MAIN_PART = "https://api.themoviedb.org/"
    private const val BASE_URL_VERSION = "3/"

    const val BASE_URL = "$BASE_URL_MAIN_PART$BASE_URL_VERSION"
    const val API_KEY = BuildConfig.API_KEY
    const val LANG = "ru"
    const val POPULARITY_SORT = "popularity.desc"
    const val VOTE_AVERAGE_SORT = "vote_average.desc"
    const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/"
    const val POSTER_SIZE_S = "w154"
    const val POSTER_SIZE_M = "w342"
    const val POSTER_SIZE_L = "w500"

    fun getOkHttpClientWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)

//        httpClient.addInterceptor { chain ->
//            val original = chain.request()
//            val request = original.newBuilder()
//                .header("api_key", "02aa4f3b7db68ceb4c5c389d5dee7ce4")
//                .method(original.method(), original.body())
//                .build()
//            chain.proceed(request)
//        }
        return httpClient.build()
    }
}