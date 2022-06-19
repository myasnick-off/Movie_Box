package com.example.moviebox._core.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientWithHeaders())
            .build()
            .create(ApiService::class.java)
    }

    private fun getOkHttpClientWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        return httpClient.build()
    }

    companion object {
        private const val BASE_URL_MAIN_PART = "https://api.themoviedb.org/"
        private const val BASE_URL_VERSION = "3/"
        private const val BASE_URL = "${BASE_URL_MAIN_PART}${BASE_URL_VERSION}"
        private const val TIMEOUT_SEC = 10L
    }

}