package com.example.moviebox.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.mapper.DtoToUiMapper
import com.example.moviebox._core.domain.uscases.GetMovieListUseCase
import com.example.moviebox._core.ui.model.ListViewState
import com.example.moviebox._core.ui.store.MainStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val store: MainStore,
    private val dtoToUiMapper: DtoToUiMapper,
    private val categoryListMapper: CategoryListMapper,
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<ListViewState> = MutableStateFlow(ListViewState.Empty)
    val viewState = _viewState.asStateFlow()

    private val _viewEffect: MutableSharedFlow<String> = MutableSharedFlow()
    val viewEffect = _viewEffect.asSharedFlow()

    private var withAdult: Boolean = false

    init {
        store.state.onEach(::renderStoreState).launchIn(viewModelScope)
        store.effect.onEach(::renderStoreEffect).launchIn(viewModelScope)
    }

    private fun renderStoreState(state: MainStore.State) {
        when (state) {
            is MainStore.State.Empty, is MainStore.State.Loading -> _viewState.value = ListViewState.Loading
            is MainStore.State.Data -> _viewState.value = ListViewState.Data(data = categoryListMapper(state.data))
            is MainStore.State.Error -> _viewState.value = ListViewState.Error(message = state.message)
            is MainStore.State.Refreshing -> _viewState.value = ListViewState.Refreshing(data = categoryListMapper(state.data))
            else -> {}
        }
    }

    private fun renderStoreEffect(storeEffect: MainStore.Effect) {
        when(storeEffect) {
            is MainStore.Effect.LoadData -> getMovieListFromServer()
            is MainStore.Effect.Error -> emitMessage(storeEffect.message)
        }
    }

    private fun emitMessage(message: String) {
        viewModelScope.launch { _viewEffect.emit(value = message) }
    }

    fun loadData(withAdult: Boolean) {
        this.withAdult = withAdult
        store.dispatch(event = MainStore.Event.Refresh)
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
                    store.dispatch(event = MainStore.Event.DataReceived(data = dtoToUiMapper(movieList)))
                }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}