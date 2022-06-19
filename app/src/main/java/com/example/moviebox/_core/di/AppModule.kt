package com.example.moviebox._core.di

import androidx.room.Room
import com.example.moviebox._core.data.local.DbUtils.DB_NAME
import com.example.moviebox._core.data.local.LocalRepositoryImpl
import com.example.moviebox._core.data.local.ProfileDataBase
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.data.remote.RemoteRepositoryImpl
import com.example.moviebox._core.data.remote.RemoteDataSource
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox.details.ui.DetailsViewModel
import com.example.moviebox.details.ui.LocalDataViewModel
import com.example.moviebox.filter.ui.GenresViewModel
import com.example.moviebox.home.ui.MainViewModel
import com.example.moviebox.profile.ui.TabViewModel
import com.example.moviebox.search.ui.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

val appModule = module {

    single { RemoteDataSource() }
    single<RemoteRepository> { RemoteRepositoryImpl(remoteDataSource = get()) }

    single { Room.databaseBuilder(androidApplication(), ProfileDataBase::class.java, DB_NAME).build() }
    single<LocalRepository> { LocalRepositoryImpl(db = get()) }
}