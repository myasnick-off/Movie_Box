package com.example.moviebox.filter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.data.remote.model.GenreDTO
import kotlinx.coroutines.launch

class GenresViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    private val liveData = MutableLiveData<GenresAppState>()
    private var genreList: List<GenreDTO>? = null    // переменная для локального хранения загруженных данных
    private var isDataLoaded = false                 // флаг наличия уже загруженных данных

    fun getLiveData(): LiveData<GenresAppState> = liveData

    fun getGenreListFromServer() {
        liveData.value = GenresAppState.Loading
        if (!isDataLoaded) {
            viewModelScope.launch {
                remoteRepository.getGenreList()
                    .onFailure { error ->
                        liveData.value = GenresAppState.Error(error)
                    }
                    .onSuccess { result ->
                        isDataLoaded = true
                        genreList = result.genres
                        liveData.value = GenresAppState.Success(result.genres)
                    }
            }
        } else {
            genreList?.let { liveData.value = GenresAppState.Success(it) } ?: run {
                isDataLoaded = false
                liveData.value = GenresAppState.Error(Throwable())
            }
        }
    }

    fun resetDataLoaded() {
        isDataLoaded = false
    }
}