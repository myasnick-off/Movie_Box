package com.example.moviebox.home.di

import com.example.moviebox._core.domain.uscases.GetCategoryListUseCase
import com.example.moviebox._core.ui.store.MainStore
import com.example.moviebox.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { (store: MainStore) -> HomeViewModel(getCategoryListUseCase = get(), store = store) }
}