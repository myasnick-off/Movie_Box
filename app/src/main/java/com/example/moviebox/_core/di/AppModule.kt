package com.example.moviebox._core.di

import androidx.room.Room
import com.example.moviebox._core.data.local.DbUtils.DB_NAME
import com.example.moviebox._core.data.local.LocalRepositoryImpl
import com.example.moviebox._core.data.local.ProfileDataBase
import com.example.moviebox._core.data.remote.ApiService
import com.example.moviebox._core.data.remote.OkHttpClient
import com.example.moviebox._core.data.remote.RemoteRepositoryImpl
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.domain.mapper.DtoToUiMapper
import com.example.moviebox._core.domain.uscases.GetMovieListUseCase
import com.example.moviebox._core.ui.store.AppStore
import com.example.moviebox.main.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { OkHttpClient.getInstance() }
    single { ApiService.getInstance(client = get()) }
    single<RemoteRepository> { RemoteRepositoryImpl(apiService = get()) }

    single { Room.databaseBuilder(androidApplication(), ProfileDataBase::class.java, DB_NAME).build() }
    single<LocalRepository> { LocalRepositoryImpl(db = get()) }

    single { AppStore() }

    factory { DtoToUiMapper() }
}