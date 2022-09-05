package com.example.moviebox

import android.app.Application
import com.example.moviebox._core.di.appModule
import com.example.moviebox.details.di.detailsModule
import com.example.moviebox.filter.di.filterModule
import com.example.moviebox.home.di.homeModule
import com.example.moviebox.main.di.mainModule
import com.example.moviebox.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                mainModule,
                homeModule,
                detailsModule,
                filterModule,
                searchModule,
            )
        }
    }
}