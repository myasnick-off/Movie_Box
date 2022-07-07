package com.example.moviebox.details.di

import com.example.moviebox.details.domain.MovieDetailsUseCase
import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.details.ui.LocalDataViewModel
import com.example.moviebox.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    factory { MovieDetailsUseCase(remoteRepository = get()) }
    viewModel { DetailsViewModel(movieDetailsUseCase = get(), localRepository = get()) }
    viewModel { LocalDataViewModel(localRepository = get()) }
}