package com.example.moviebox.search.di

import com.example.moviebox.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(remoteRepository = get()) }
}