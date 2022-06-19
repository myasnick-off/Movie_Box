package com.example.moviebox.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.LocalRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TabViewModel: ViewModel(), KoinComponent {

    private val localRepository: LocalRepository by inject()
    private val liveData = MutableLiveData<ProfileAppState>()

    fun getProfileLiveData(): LiveData<ProfileAppState> = liveData

    fun getAllHistory() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = localRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun getAllFavorite() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = localRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun getAllWishlist() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = localRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromHistory(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.deleteEntityFromHistory(movie)
            val movieList = localRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromFavorite(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.deleteEntityFromFavorite(movie)
            val movieList = localRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromWishlist(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.deleteEntityFromWishList(movie)
            val movieList = localRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun clearHistory() {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.clearAllHistory()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }

    fun clearFavorite() {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.clearAllFavorite()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }

    fun clearWishlist() {
        liveData.value = ProfileAppState.Loading
        Thread {
            localRepository.clearAllWishList()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }


}