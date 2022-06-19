package com.example.moviebox._core.data.remote

import com.example.moviebox.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {

    const val API_KEY = BuildConfig.API_KEY
    const val LANG = "ru"
    const val POPULARITY_SORT = "popularity.desc"
    const val VOTE_AVERAGE_SORT = "vote_average.desc"
    const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/"
    const val POSTER_SIZE_S = "w154"
    const val POSTER_SIZE_M = "w342"
    const val POSTER_SIZE_L = "w500"
}