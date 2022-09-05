package com.example.moviebox._core.ui.store

import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResultStore : CoroutineScope by MainScope() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Empty)
    val state = _state.asStateFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    fun dispatch(event: Event) {
        val currentState = state.value
        val newState = reduce(event = event, currentState = currentState)
        if (currentState != newState) {
            _state.value = newState
        }
    }

    private fun reduce(event: Event, currentState: State): State {
        return when(event) {
            is Event.Refresh -> {
                when(currentState) {
                    is State.Empty, is State.Error -> {
                        launch { _effect.emit(Effect.LoadData(page = INITIAL_PAGE)) }
                        State.Loading
                    }
                    is State.Data -> {
                        launch { _effect.emit(Effect.LoadData(page = INITIAL_PAGE)) }
                        State.Refreshing(data = currentState.data, page = currentState.page, loadMore = true)
                    }
                    is State.FullData -> {
                        launch { _effect.emit(Effect.LoadData(page = INITIAL_PAGE)) }
                        State.Refreshing(data = currentState.data, page = currentState.page, loadMore = false)
                    }
                    else -> currentState
                }
            }
            is Event.LoadMore -> {
                when (currentState) {
                    is State.Data -> {
                        launch { _effect.emit(Effect.LoadData(page = currentState.page + 1)) }
                        State.MoreLoading(data = currentState.data, page = currentState.page)
                    }
                    else -> currentState
                }
            }
            is Event.ErrorReceived -> {
                when(currentState) {
                    is State.Loading -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Error(message = event.message)
                    }
                    is State.Refreshing -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        if  (currentState.loadMore) {
                            State.Data(data = currentState.data, page = currentState.page)
                        } else {
                            State.FullData(data = currentState.data, page = currentState.page)
                        }
                    }
                    is State.MoreLoading -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Data(data = currentState.data, page = currentState.page)
                    }
                    else -> currentState
                }
            }
            is Event.DataReceived -> {
                when(currentState) {
                    is State.Loading, is State.Refreshing -> {
                        if (event.data.isEmpty()) {
                            State.Empty
                        } else {
                            if (event.loadMore) {
                                State.Data(data = event.data, page = event.page)
                            } else {
                                State.FullData(data = event.data, page = event.page)
                            }
                        }
                    }
                    is State.MoreLoading -> {
                        if (event.data.isEmpty() || !event.loadMore) {
                            State.FullData(data = currentState.data + event.data, page = event.page)
                        } else {
                            State.Data(data = currentState.data + event.data, page = event.page)
                        }
                    }
                    else -> currentState
                }
            }
        }
    }

    sealed class Event {
        object Refresh : Event()
        object LoadMore: Event()
        data class DataReceived(val data: List<MovieItem>, val page: Int, val loadMore: Boolean) : Event()
        data class ErrorReceived(val message: String) : Event()
    }

    sealed class State {
        object Empty : State()
        object Loading : State()
        data class MoreLoading(val data: List<MovieItem>, val page: Int) : State()
        data class Refreshing(val data: List<MovieItem>, val page: Int, val loadMore: Boolean) : State()
        data class Error(val message: String) : State()
        data class Data(val data: List<MovieItem>, val page: Int) : State()
        data class FullData(val data: List<MovieItem>, val page: Int) : State()
    }

    sealed class Effect {
        data class Error(val message: String) : Effect()
        data class LoadData(val page: Int): Effect()
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}