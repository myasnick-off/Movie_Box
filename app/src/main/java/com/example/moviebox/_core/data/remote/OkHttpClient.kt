package com.example.moviebox._core.data.remote

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClient {

    private const val TIMEOUT_SEC = 10L

    fun getInstance(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        return httpClient.build()
    }
}