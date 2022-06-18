package com.example.moviebox._core.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieSearchRepo {

    val api: MovieSearchAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHttpClientWithHeaders())
            .build()
        adapter.create(MovieSearchAPI::class.java)
    }
}