package com.example.moviebox.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.AppState
import com.example.moviebox.model.entities.Category
import com.example.moviebox.model.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()
    private var categoryList: List<Category>? = null    // переменная для локального хранения загруженных данных
    private var isDataLoaded = false                    // флаг наличия уже загруженных данных

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovieListFromServer(withAdult: Boolean) {
        liveData.value = AppState.Loading
        // Если данные еще на загружались с сервера, то инициируем их загрузку в отдельном потоке
        if (!isDataLoaded) {
            Thread {
                categoryList = repository.getCategoryListFromServer(withAdult)
                if (categoryList != null) {                              // в случае успешной загрузки:
                    isDataLoaded = true                                  // устанавливаем флаг наличия данных
                    liveData.postValue(AppState.Success(categoryList!!)) // и постим данные в liveData
                } else {                                                 // в случае неудачной загрузки:
                    liveData.postValue(AppState.Error(Throwable()))      // и постим в liveData ошибку
                }
            }.start()
        } else {                                                    // если данные уже загружались ранее
            if (categoryList != null) {                             // и если они не null
                liveData.value = AppState.Success(categoryList!!)   // то помещаем их в liveData
            } else {
                isDataLoaded = false                                // иначе сбрасывам флаг наличия данных
                liveData.value = AppState.Error(Throwable())        // и помещаем в liveData ошибку
            }
        }
    }

    fun resetDataLoaded() {
        isDataLoaded = false
    }

}