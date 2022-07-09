package com.example.moviebox.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox.profile.ui.ProfileAppState
import com.example.moviebox.search.domain.FilterUseCase
import com.example.moviebox.search.domain.SearchUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel() {

    private val liveData = MutableLiveData<ProfileAppState>()

    fun getLiveData(): LiveData<ProfileAppState> = liveData

    fun searchRequest(phrase: String, withAdult: Boolean) {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            searchUseCase(phrase = phrase, withAdult = withAdult)
                .onFailure { error ->
                    liveData.value = ProfileAppState.Error(error = error)
                }
                .onSuccess { data ->
                    liveData.value = ProfileAppState.Success(movieList = data.results)
                }
        }
    }

    fun filterSearchRequest(filterSet: FilterSet, withAdult: Boolean) {
        liveData.value = ProfileAppState.Loading
        viewModelScope.launch {
            filterUseCase(filterSet = filterSet, withAdult = withAdult)
                .onFailure { error ->
                    liveData.value = ProfileAppState.Error(error = error)
                }
                .onSuccess { data ->
                    liveData.value = ProfileAppState.Success(movieList = data.results)
                }
        }
    }
}