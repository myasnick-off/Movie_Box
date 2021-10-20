package com.example.moviebox.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.model.repository.Repository
import kotlin.math.roundToInt

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<DetailsAppState>()

    fun getLiveData(): LiveData<DetailsAppState> = liveData

    fun getMovieDetails(id: Int) = getMovieDetailsFromServer(id)

    private fun getMovieDetailsFromServer(id: Int){
        liveData.value = DetailsAppState.Loading
        Thread {
            val movieData = repository.getMovieDataFromServer(id)
            if (movieData != null) {
                liveData.postValue(DetailsAppState.Success(movieData))
            } else {
                liveData.postValue(DetailsAppState.Error(Throwable()))
            }
        }.start()
    }

    private fun getMovieDetailsFromLocal(){
        liveData.value = DetailsAppState.Loading
        Thread {
            Thread.sleep(1000)
            // рандомизатор загрузки данных
            when (Math.random().roundToInt()) {
                1 -> liveData.postValue(DetailsAppState.Success(repository.getMovieDataFromLocal()))
                0 -> liveData.postValue(DetailsAppState.Error(Throwable()))
            }
        }.start()
    }
}