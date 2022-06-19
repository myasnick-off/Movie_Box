package com.example.moviebox.home.di

import com.example.moviebox.home.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { MainViewModel(remoteRepository = get()) }
}