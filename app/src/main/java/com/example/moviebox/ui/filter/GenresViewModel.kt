package com.example.moviebox.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.model.repository.Repository
import com.example.moviebox.model.rest_entities.GenreDTO

class GenresViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<GenresAppState>()
    private var genreList: List<GenreDTO>? = null    // переменная для локального хранения загруженных данных
    private var isDataLoaded = false                 // флаг наличия уже загруженных данных

    fun getLiveData(): LiveData<GenresAppState> = liveData

    fun getGenreListFromServer() {
        liveData.value = GenresAppState.Loading
        // Если данные еще на загружались с сервера, то инициируем их загрузку в отдельном потоке
        if (!isDataLoaded) {
            Thread {
                genreList = repository.getGenreListFromServer()?.genres
                if (genreList != null) {                                    // в случае успешной загрузки:
                    isDataLoaded = true                                     // устанавливаем флаг наличия данных
                    liveData.postValue(GenresAppState.Success(genreList!!)) // и постим данные в liveData
                } else {                                                    // в случае неудачной загрузки:
                    liveData.postValue(GenresAppState.Error(Throwable()))   // постим в liveData ошибку
                }
            }.start()
        } else {                                                        // если данные уже загружались ранее
            if (genreList != null) {                                    // и если они не null
                liveData.value = GenresAppState.Success(genreList!!)    // то помещаем их в liveData
            } else {
                isDataLoaded = false                                    // иначе сбрасывам флаг наличия данных
                liveData.value = GenresAppState.Error(Throwable())      // и помещаем в liveData ошибку
            }
        }
    }

    fun resetDataLoaded() {
        isDataLoaded = false
    }

}