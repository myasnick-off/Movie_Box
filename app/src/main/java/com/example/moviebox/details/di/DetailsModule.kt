package com.example.moviebox.details.di

import com.example.moviebox.details.domain.GetMovieDetailsUseCase
import com.example.moviebox.details.domain.MovieDetailsMapper
import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.details.ui.LocalDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    factory { MovieDetailsMapper() }
    factory { GetMovieDetailsUseCase(remoteRepository = get(), localRepository = get(), mapper = get()) }
    viewModel { DetailsViewModel(localRepository = get(), getMovieDetailsUseCase = get()) }
    viewModel { LocalDataViewModel(localRepository = get()) }
}