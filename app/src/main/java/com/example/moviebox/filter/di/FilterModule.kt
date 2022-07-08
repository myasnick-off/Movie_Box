package com.example.moviebox.filter.di

import com.example.moviebox.filter.ui.GenresViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filterModule = module {
    viewModel { GenresViewModel(remoteRepository = get()) }
}