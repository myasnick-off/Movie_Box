package com.example.moviebox.di

import com.example.moviebox._core.domain.Repository
import com.example.moviebox._core.data.RepositoryImpl
import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.filter.ui.GenresViewModel
import com.example.moviebox.home.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { GenresViewModel(get()) }
}