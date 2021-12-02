package com.example.moviebox.di

import com.example.moviebox.model.repository.Repository
import com.example.moviebox.model.repository.RepositoryImpl
import com.example.moviebox.ui.details.DetailsViewModel
import com.example.moviebox.ui.filter.GenresViewModel
import com.example.moviebox.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { GenresViewModel(get()) }
}