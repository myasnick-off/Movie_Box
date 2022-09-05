package com.example.moviebox.main.di

import com.example.moviebox._core.domain.uscases.GetMovieListUseCase
import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox.main.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory { MainStore() }
    factory { GetMovieListUseCase(remoteRepository = get()) }
    viewModel { (store: MainStore) -> MainViewModel(store = store, mapper = get(), getMovieListUseCase = get()) }
}