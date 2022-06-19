package com.example.moviebox.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.App.Companion.getDataBase
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.domain.LocalRepository
import com.example.moviebox._core.data.local.LocalRepositoryImpl

class TabViewModel(
    private val profileRepository: LocalRepository = LocalRepositoryImpl(getDataBase())
) : ViewModel() {

    private val liveData = MutableLiveData<ProfileAppState>()

    fun getProfileLiveData(): LiveData<ProfileAppState> = liveData

    fun getAllHistory() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = profileRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun getAllFavorite() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = profileRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun getAllWishlist() {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = profileRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromHistory(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.deleteEntityFromHistory(movie)
            val movieList = profileRepository.getAllHistory()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromFavorite(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.deleteEntityFromFavorite(movie)
            val movieList = profileRepository.getAllFavorite()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun deleteFromWishlist(movie: MovieDTO) {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.deleteEntityFromWishList(movie)
            val movieList = profileRepository.getAllWishList()
            liveData.postValue(ProfileAppState.Success(movieList))
        }.start()
    }

    fun clearHistory() {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.clearAllHistory()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }

    fun clearFavorite() {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.clearAllFavorite()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }

    fun clearWishlist() {
        liveData.value = ProfileAppState.Loading
        Thread {
            profileRepository.clearAllWishList()
            liveData.postValue(ProfileAppState.Success(arrayListOf()))
        }.start()
    }


}