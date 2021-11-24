package com.example.moviebox.model.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GenreListRepo {

    val api: GenreListAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHttpClientWithHeaders())
            .build()
        adapter.create(GenreListAPI::class.java)
    }
}