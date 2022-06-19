package com.example.moviebox.details.di

import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.details.ui.LocalDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    viewModel { DetailsViewModel(remoteRepository = get(), localRepository = get()) }
    viewModel { LocalDataViewModel(localRepository = get()) }
}