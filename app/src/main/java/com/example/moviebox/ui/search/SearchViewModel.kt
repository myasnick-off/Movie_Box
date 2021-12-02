package com.example.moviebox.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviebox.model.entities.FilterSet
import com.example.moviebox.model.repository.Repository
import com.example.moviebox.model.repository.RepositoryImpl
import com.example.moviebox.ui.profile.ProfileAppState

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