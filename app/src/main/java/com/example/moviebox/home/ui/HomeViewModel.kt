package com.example.moviebox.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.ui.model.AppState
import com.example.moviebox.home.domain.MovieListUseCase
import com.example.moviebox.home.domain.model.Category
import kotlinx.coroutines.launch

class HomeViewModel(private val movieListUseCase: MovieListUseCase) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()
    private var categoryList: List<Category>? = null    // переменная для локального хранения загруженных данных
    private var isDataLoaded = false                    // флаг наличия уже загруженных данных

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovieListFromServer(withAdult: Boolean) {
        liveData.value = AppState.Loading
        if (!isDataLoaded) {
            viewModelScope.launch {
                movieListUseCase(withAdult)
                    .onFailure { error ->
                        liveData.postValue(AppState.Error(error))
                    }
                    .onSuccess { categoryList ->
                        if (categoryList.isNotEmpty()) {
                            isDataLoaded = true
                            liveData.value = AppState.Success(categoryList)
                        } else {
                            isDataLoaded = false
                            liveData.value = AppState.Error(Throwable())
                        }
                    }
            }
        } else {
            categoryList?.let { liveData.value = AppState.Success(it) } ?: run {
                isDataLoaded = false
                liveData.value = AppState.Error(Throwable())
            }
        }
    }

    fun resetDataLoaded() {
        isDataLoaded = false
    }

}