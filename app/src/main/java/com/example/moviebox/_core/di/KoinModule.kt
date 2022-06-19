package com.example.moviebox._core.di

import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.data.remote.RemoteRepositoryImpl
import com.example.moviebox._core.data.remote.RemoteDataSource
import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.filter.ui.GenresViewModel
import com.example.moviebox.home.ui.MainViewModel
import com.example.moviebox.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RemoteDataSource() }
    single<RemoteRepository> { RemoteRepositoryImpl(remoteDataSource = get()) }

    viewModel { MainViewModel(remoteRepository = get()) }
    viewModel { DetailsViewModel(remoteRepository = get()) }
    viewModel { GenresViewModel(remoteRepository = get()) }
    viewModel { SearchViewModel(remoteRepository = get()) }
}