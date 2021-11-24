package com.example.moviebox.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.App.Companion.getDataBase
import com.example.moviebox.room.LocalRepository
import com.example.moviebox.room.LocalRepositoryImpl

class LocalDataViewModel(
    private val profileRepository: LocalRepository = LocalRepositoryImpl(getDataBase())
) : ViewModel() {

    private val liveData = MutableLiveData<LocalDataAppState>()

    fun getLiveData(): LiveData<LocalDataAppState> = liveData

    fun checkInLocalDB(movieId: Long) {
        liveData.value = LocalDataAppState.Loading
        Thread {
            val hasInFavorite = profileRepository.checkMovieInFavorite(movieId)
            val hasInWishlist = profileRepository.checkMovieInWishlist(movieId)
            liveData.postValue(LocalDataAppState.Success(listOf(hasInFavorite, hasInWishlist)))
        }.start()
    }
}