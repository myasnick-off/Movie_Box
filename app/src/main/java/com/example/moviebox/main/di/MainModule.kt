package com.example.moviebox.main.di

import com.example.moviebox._core.domain.uscases.GetMovieListUseCase
import com.example.moviebox.main.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory { GetMovieListUseCase(remoteRepository = get()) }
    viewModel { MainViewModel(store = get(), mapper = get(), getMovieListUseCase = get()) }
}