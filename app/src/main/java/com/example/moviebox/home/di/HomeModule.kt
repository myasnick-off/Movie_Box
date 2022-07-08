package com.example.moviebox.home.di

import com.example.moviebox.home.domain.MovieListUseCase
import com.example.moviebox.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { MovieListUseCase(remoteRepository = get()) }
    viewModel { HomeViewModel(movieListUseCase = get()) }
}