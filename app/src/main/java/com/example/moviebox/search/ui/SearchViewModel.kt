package com.example.moviebox.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox._core.ui.model.FilterSet
import com.example.moviebox._core.domain.RemoteRepository
import com.example.moviebox._core.data.remote.RemoteRepositoryImpl
import com.example.moviebox.profile.ui.ProfileAppState

class SearchViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val liveData = MutableLiveData<ProfileAppState>()

    fun getLiveData(): LiveData<ProfileAppState> = liveData

    fun searchRequest(phrase: String, withAdult: Boolean) {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = remoteRepository.searchByPhrase(phrase, withAdult)
            if (movieList != null) {
                liveData.postValue(ProfileAppState.Success(movieList))
            }
            else {
                liveData.postValue(ProfileAppState.Error(Throwable()))
            }
        }.start()
    }

    fun filterSearchRequest(filterSet: FilterSet, withAdult: Boolean) {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = remoteRepository.filterSearch(filterSet, withAdult)
            if (movieList != null) {
                liveData.postValue(ProfileAppState.Success(movieList))
            }
            else {
                liveData.postValue(ProfileAppState.Error(Throwable()))
            }
        }.start()
    }
}