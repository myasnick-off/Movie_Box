package com.example.moviebox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.AppState
import com.example.moviebox.model.repository.Repository
import java.lang.Thread.sleep
import kotlin.math.roundToInt

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovieList() = getMovieListFromServer()

    private fun getMovieListFromLocalSource() {
        liveData.value = AppState.Loading
        Thread {
            sleep(1000)
            // рандомизатор загрузки данных
            when (Math.random().roundToInt()) {
                1 -> liveData.postValue(AppState.Success(repository.getCategoryListFromLocal()))
                0 -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }

    private fun getMovieListFromServer() {
        liveData.value = AppState.Loading
        Thread {
            val categoryList = repository.getCategoryListFromServer()
            if (categoryList != null) {
                liveData.postValue(AppState.Success(categoryList))
            } else {
                liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }
}