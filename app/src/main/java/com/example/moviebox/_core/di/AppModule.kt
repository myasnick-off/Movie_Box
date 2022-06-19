package com.example.moviebox._core.di

import androidx.room.Room
import com.example.moviebox._core.data.local.DbUtils.DB_NAME
import com.example.moviebox._core.data.local.LocalRepositoryImpl
import com.example.moviebox._core.data.local.ProfileDataBase
import com.example.moviebox._core.data.remote.RemoteDataSource
import com.example.moviebox._core.data.remote.RemoteRepositoryImpl
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.domain.RemoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single { RemoteDataSource() }
    single<RemoteRepository> { RemoteRepositoryImpl(remoteDataSource = get()) }

    single { Room.databaseBuilder(androidApplication(), ProfileDataBase::class.java, DB_NAME).build() }
    single<LocalRepository> { LocalRepositoryImpl(db = get()) }
}