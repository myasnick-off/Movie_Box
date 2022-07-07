package com.example.moviebox.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.LocalRepository
import kotlinx.coroutines.launch

class LocalDataViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val liveData = MutableLiveData<LocalDataAppState>()

    fun getLiveData(): LiveData<LocalDataAppState> = liveData

    fun checkInLocalDB(movieId: Long) {
        liveData.value = LocalDataAppState.Loading
        viewModelScope.launch {
            val hasInFavorite = localRepository.checkMovieInFavorite(movieId)
            val hasInWishlist = localRepository.checkMovieInWishlist(movieId)
            liveData.value = LocalDataAppState.Success(listOf(hasInFavorite, hasInWishlist))
        }.start()
    }
}