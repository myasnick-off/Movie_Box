package com.example.moviebox.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.mapper.DtoToUiMapper
import com.example.moviebox._core.domain.uscases.GetMovieListUseCase
import com.example.moviebox._core.ui.store.MainStore
import kotlinx.coroutines.launch

class MainViewModel(
    private val store: MainStore,
    private val mapper: DtoToUiMapper,
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private var withAdult: Boolean = false

    fun loadData(withAdult: Boolean) {
        this.withAdult = withAdult
        store.dispatch(event = MainStore.Event.Refresh)
        getMovieListFromServer()
    }

    private fun getMovieListFromServer() {
        viewModelScope.launch {
            getMovieListUseCase(withAdult = withAdult)
                .onFailure { error ->
                    store.dispatch(
                        event = MainStore.Event.ErrorReceived(
                            message = error.message ?: DEFAULT_ERROR_MESSAGE
                        )
                    )
                }
                .onSuccess { movieList ->
                    store.dispatch(event = MainStore.Event.DataReceived(data = mapper(movieList)))
                }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}