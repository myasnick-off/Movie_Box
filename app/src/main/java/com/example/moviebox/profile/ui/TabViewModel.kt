package com.example.moviebox.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.LocalRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TabViewModel: ViewModel(), KoinComponent {

    private val localRepository: LocalRepository by inject()
    private val liveData = MutableLiveData<ProfileAppState>()

    fun getProfileLiveData(): LiveData<ProfileAppState> = liveData

    fun getAllHistory() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            val movieList = localRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }
    }

    fun getAllFavorite() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            val movieList = localRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }
    }

    fun getAllWishlist() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            val movieList = localRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromHistory(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.deleteEntityFromHistory(movie)
            val movieList = localRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }
    }

    fun deleteFromFavorite(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.deleteEntityFromFavorite(movie)
            val movieList = localRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }
    }

    fun deleteFromWishlist(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.deleteEntityFromWishList(movie)
            val movieList = localRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }
    }

    fun clearHistory() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.clearAllHistory()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }
    }

    fun clearFavorite() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.clearAllFavorite()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }
    }

    fun clearWishlist() {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            localRepository.clearAllWishList()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }
    }


}