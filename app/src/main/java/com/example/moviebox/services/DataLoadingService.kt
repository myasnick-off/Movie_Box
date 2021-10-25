package com.example.moviebox.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviebox.model.MovieLoader
import com.example.moviebox.model.entities.Category

/**
 * Сервис для загрузки списка фильмов с сервера
 * */
class DataLoadingService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            sendServiceBroadcast(INTENT_SERVICE_LOADING, null)
            val categoryList = getCategoryListFromServer()
            if (categoryList != null) {
                sendServiceBroadcast(INTENT_SERVICE_SUCCESS, categoryList)
            } else {
                sendServiceBroadcast(INTENT_SERVICE_ERROR, null)
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    // метод формирования и оправки локального широковещательнго сообщения
    private fun sendServiceBroadcast(result: String, dataList: List<Category>?) {
        val broadcastIntent = Intent()
        when (result) {
            INTENT_SERVICE_LOADING -> broadcastIntent.putExtra(INTENT_SERVICE_STATUS, result)
            INTENT_SERVICE_SUCCESS -> {
                broadcastIntent.putExtra(INTENT_SERVICE_STATUS, result)
                broadcastIntent.putExtra(INTENT_SERVICE_DATA, dataList?.toTypedArray())
            }
            INTENT_SERVICE_ERROR -> broadcastIntent.putExtra(INTENT_SERVICE_STATUS, result)
        }
        broadcastIntent.action = INTENT_ACTION_KEY
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun getCategoryListFromServer(): List<Category>? {
        // создаем пустой массив со списками фильмов по жанрам
        val categoryList = arrayListOf<Category>()
        // получаем с сервера список жанров (id жанров и их названия)
        val genreList = MovieLoader.loadGenreList()

        // заполяем массив со списками фильмов по жанрам в соответсвии со списком жанров, полученном с сервера
        genreList?.let {
            for (i in 0 until genreList.genres.size) {
                val genre = genreList.genres[i]
                val movieList = MovieLoader.loadMovieListByGenre(intArrayOf(genre.id))
                movieList?.let { categoryList.add(Category(genre.name, movieList.results)) }
            }
            if (categoryList.isNotEmpty()) return categoryList
        }
        return null
    }

    companion object {
        const val INTENT_ACTION_KEY = "com.example.moviebox.services.DataLoadingService.ActionKey"
        const val INTENT_SERVICE_STATUS = "process_status"
        const val INTENT_SERVICE_SUCCESS = "data_loaded_successful"
        const val INTENT_SERVICE_LOADING = "data_is_loading"
        const val INTENT_SERVICE_ERROR = "loading_error"
        const val INTENT_SERVICE_DATA = "loaded data"

        // метод запуска сервиса
        fun start(context: Context) {
            val serviceIntent = Intent(context, DataLoadingService::class.java)
            context.startService(serviceIntent)
        }

        // метод остановки сервиса
        fun stop(context: Context) {
            val serviceIntent = Intent(context, DataLoadingService::class.java)
            context.stopService(serviceIntent)
        }
    }
}