package com.example.moviebox.home.di

import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox.home.ui.CategoryListMapper
import com.example.moviebox.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { CategoryListMapper() }
    viewModel { (store: MainStore) ->
        HomeViewModel(
            store = store,
            dtoToUiMapper = get(),
            categoryListMapper = get(),
            getMovieListUseCase = get()
        )
    }
}