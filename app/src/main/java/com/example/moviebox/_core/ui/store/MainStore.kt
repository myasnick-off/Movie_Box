package com.example.moviebox._core.ui.store

import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainStore : CoroutineScope by MainScope() {

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
                        launch { _effect.emit(Effect.LoadData) }
                        State.Loading
                    }
                    is State.Data -> {
                        launch { _effect.emit(Effect.LoadData) }
                        State.Refreshing(data = currentState.data)
                    }
                    else -> currentState
                }
            }
            is Event.LoadMore -> {
                currentState
            }
            is Event.ErrorReceived -> {
                when(currentState) {
                    is State.Loading -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Error(message = event.message)
                    }
                    is State.Refreshing -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Data(data = currentState.data)
                    }
                    else -> currentState
                }
            }
            is Event.DataReceived -> {
                when(currentState) {
                    is State.Loading, is State.Refreshing -> {
                        State.Data(data = event.data)
                    }
                    else -> currentState
                }
            }

        }
    }

    sealed class Event {
        object Refresh : Event()
        data class LoadMore(val data: List<MovieItem>): Event()
        data class DataReceived(val data: List<MovieItem>) : Event()
        data class ErrorReceived(val message: String) : Event()
    }

    sealed class State {
        object Empty : State()
        object Loading : State()
        data class MoreLoading(val data: List<MovieItem>) : State()
        data class Refreshing(val data: List<MovieItem>) : State()
        data class Error(val message: String) : State()
        data class Data(val data: List<MovieItem>) : State()
    }

    sealed class Effect() {
        data class Error(val message: String) : Effect()
        object LoadData: Effect()
    }
}