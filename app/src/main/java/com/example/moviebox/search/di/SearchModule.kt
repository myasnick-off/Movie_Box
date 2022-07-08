package com.example.moviebox.search.di

import com.example.moviebox.search.domain.FilterUseCase
import com.example.moviebox.search.domain.SearchUseCase
import com.example.moviebox.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    factory { SearchUseCase(remoteRepository = get()) }
    factory { FilterUseCase(remoteRepository = get()) }
    viewModel { SearchViewModel(searchUseCase = get(), filterUseCase = get()) }
}