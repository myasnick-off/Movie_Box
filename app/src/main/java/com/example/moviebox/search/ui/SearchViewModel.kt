package com.example.moviebox.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox._core.ui.model.FilterSet
import com.example.moviebox._core.domain.Repository
import com.example.moviebox._core.data.RepositoryImpl
import com.example.moviebox.profile.ui.ProfileAppState

class SearchViewModel(
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    private val liveData = MutableLiveData<ProfileAppState>()

    fun getLiveData(): LiveData<ProfileAppState> = liveData

    fun searchRequest(phrase: String, withAdult: Boolean) {
        liveData.value = ProfileAppState.Loading
        Thread {
            val movieList = repository.searchByPhrase(phrase, withAdult)
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
            val movieList = repository.filterSearch(filterSet, withAdult)
            if (movieList != null) {
                liveData.postValue(ProfileAppState.Success(movieList))
            }
            else {
                liveData.postValue(ProfileAppState.Error(Throwable()))
            }
        }.start()
    }
}