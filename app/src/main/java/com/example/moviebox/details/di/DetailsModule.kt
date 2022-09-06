package com.example.moviebox.details.di

import com.example.moviebox.details.domain.GetMovieDetailsUseCase
import com.example.moviebox.details.domain.MovieDetailsMapper
import com.example.moviebox.details.domain.MovieEntityMapper
import com.example.moviebox.details.ui.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    factory { MovieDetailsMapper() }
    factory { MovieEntityMapper() }
    factory { GetMovieDetailsUseCase(remoteRepository = get(), mapper = get()) }
    viewModel { (movieId: Long) -> DetailsViewModel(movieId = movieId, localRepository = get(), getMovieDetailsUseCase = get()) }
}