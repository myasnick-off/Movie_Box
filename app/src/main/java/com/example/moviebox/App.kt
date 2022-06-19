package com.example.moviebox

import android.app.Application
import androidx.room.Room
import com.example.moviebox._core.di.appModule
import com.example.moviebox._core.data.local.ProfileDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.IllegalStateException

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        private var appInstance: App? = null
        private var db: ProfileDataBase? = null
        private const val DB_NAME = "Profile.db"

        fun getDataBase(): ProfileDataBase {
            if (db == null) {
                synchronized(ProfileDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        ProfileDataBase::class.java,
                        DB_NAME
                    ).build()
                    }
                }
            }
            return db!!
        }
    }
}