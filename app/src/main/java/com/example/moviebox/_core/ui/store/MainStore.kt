package com.example.moviebox._core.ui.store

import com.example.moviebox.home.domain.model.Category
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
        val oldState = state.value
        val newState = reduce(event = event, oldState = oldState)
        if (oldState != newState) {
            _state.value = newState
        }
    }

    private fun reduce(event: Event, oldState: State): State {
        return when(event) {
            Event.Refresh -> {
                when(oldState) {
                    is State.Empty, is State.Error -> {
                        launch { _effect.emit(Effect.Load) }
                        State.Loading
                    }
                    is State.Data -> {
                        launch { _effect.emit(Effect.Load) }
                        State.Reloading(data = oldState.data)
                    }
                    else -> oldState
                }
            }
            is Event.Error -> {
                when(oldState) {
                    is State.Loading -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Error(message = event.message)
                    }
                    is State.Reloading -> {
                        launch { _effect.emit(Effect.Error(message = event.message)) }
                        State.Data(data = oldState.data)
                    }
                    else -> oldState
                }
            }
            is Event.Success -> {
                when(oldState) {
                    is State.Loading, is State.Reloading -> {
                        State.Data(data = event.data)
                    }
                    else -> oldState
                }
            }
        }
    }


    sealed class Event {
        object Refresh : Event()
        data class Success( val data: List<Category>) : Event()
        data class Error(val message: String) : Event()
    }

    sealed class State {
        object Empty : State()
        object Loading : State()
        data class Error(val message: String) : State()
        data class Data(val data: List<Category>) : State()
        data class Reloading(val data: List<Category>) : State()
    }

    sealed class Effect() {
        data class Error(val message: String) : Effect()
        object Load: Effect()
    }
}