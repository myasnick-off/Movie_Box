package com.example.moviebox.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.mapper.DtoToUiMapper
import com.example.moviebox._core.ui.model.ListViewState
import com.example.moviebox._core.ui.store.ResultStore
import com.example.moviebox.filter.ui.model.FilterSet
import com.example.moviebox.search.domain.FilterUseCase
import com.example.moviebox.search.domain.SearchUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val store: ResultStore,
    private val searchUseCase: SearchUseCase,
    private val filterUseCase: FilterUseCase,
    private val mapper: DtoToUiMapper
) : ViewModel() {

    private var phrase: String? = null
    private var filterSet: FilterSet? = null
    private var withAdult: Boolean = false

    private val _viewState: MutableStateFlow<ListViewState> = MutableStateFlow(ListViewState.Empty)
    val viewState: StateFlow<ListViewState> get() = _viewState.asStateFlow()

    private val _viewEffect: MutableSharedFlow<String> = MutableSharedFlow()
    val viewEffect: SharedFlow<String> get() = _viewEffect.asSharedFlow()

    init {
        store.state.onEach { renderState(it) }.launchIn(viewModelScope)
        store.effect.onEach { renderEffect(it) }.launchIn(viewModelScope)
    }

    fun searchByPhrase(phrase: String, withAdult: Boolean) {
        this.phrase = phrase
        this.withAdult = withAdult
        store.dispatch(ResultStore.Event.Refresh)
    }

    fun searchByFilter(filterSet: FilterSet, withAdult: Boolean) {
        this.filterSet = filterSet
        this.withAdult = withAdult
        store.dispatch(ResultStore.Event.Refresh)
    }

    fun loadNextPage() {
        store.dispatch(ResultStore.Event.LoadMore)
    }

    private fun renderState(storeState: ResultStore.State) {
        when(storeState) {
            ResultStore.State.Empty -> _viewState.value = ListViewState.Empty
            ResultStore.State.Loading -> _viewState.value = ListViewState.Loading
            is ResultStore.State.Data -> _viewState.value = ListViewState.Data(data = storeState.data, loadMore = true)
            is ResultStore.State.Error -> _viewState.value = ListViewState.Error(message = storeState.message)
            is ResultStore.State.FullData -> _viewState.value = ListViewState.Data(data = storeState.data, loadMore = false)
            is ResultStore.State.MoreLoading -> _viewState.value = ListViewState.MoreLoading(data = storeState.data)
            is ResultStore.State.Refreshing -> _viewState.value = ListViewState.Refreshing(data = storeState.data)
        }
    }

    private fun renderEffect(effect: ResultStore.Effect) {
        when(effect) {
            is ResultStore.Effect.Error -> emitError(message = effect.message)
            is ResultStore.Effect.LoadData -> {
                phrase?.let { getMoviesByPhrase(phrase = it, pageNum = effect.page) }
                filterSet?.let { getMoviesByFilter(filterSet = it, pageNum = effect.page) }
            }
        }
    }

    private fun getMoviesByPhrase(phrase: String, pageNum: Int) {
        viewModelScope.launch {
            searchUseCase(phrase = phrase, withAdult = withAdult, page = pageNum)
                .onFailure { error ->
                    store.dispatch(event = ResultStore.Event.ErrorReceived(message = error.message ?: DEFAULT_ERROR_MESSAGE))
                }
                .onSuccess { data ->
                    store.dispatch(
                        event = ResultStore.Event.DataReceived(
                            data = mapper(data.results),
                            page = data.page,
                            loadMore = data.totalPages > data.page
                        )
                    )
                }
        }
    }

    private fun getMoviesByFilter(filterSet: FilterSet, pageNum: Int) {
        viewModelScope.launch {
            filterUseCase(filterSet = filterSet, withAdult = withAdult, page = pageNum)
                .onFailure { error ->
                    store.dispatch(event = ResultStore.Event.ErrorReceived(message = error.message ?: DEFAULT_ERROR_MESSAGE))
                }
                .onSuccess { data ->
                    store.dispatch(
                        event = ResultStore.Event.DataReceived(
                            data = mapper(data.results),
                            page = data.page,
                            loadMore = data.totalPages > data.page
                        )
                    )
                }
        }
    }

    private fun emitError(message: String) {
        viewModelScope.launch { _viewEffect.emit(message) }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown Error!"
    }
}