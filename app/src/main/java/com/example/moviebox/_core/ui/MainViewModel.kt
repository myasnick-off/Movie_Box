package com.example.moviebox._core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox._core.domain.uscases.GetCategoryListUseCase
import com.example.moviebox._core.ui.store.MainStore
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val store: MainStore,
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private var withAdult: Boolean = false

    init {
        store.effect.onEach(::renderStoreEffect).launchIn(viewModelScope)
    }

    fun loadData(withAdult: Boolean) {
        this.withAdult = withAdult
        store.dispatch(event = MainStore.Event.Refresh)
    }

    private fun renderStoreEffect(storeEffect: MainStore.Effect) {
        when(storeEffect) {
            MainStore.Effect.Load -> getCategoryListFromServer()
            else -> {}
        }
    }

    private fun getCategoryListFromServer() {
        viewModelScope.launch {
            getCategoryListUseCase(withAdult = withAdult)
                .onFailure { error ->
                    store.dispatch(
                        event = MainStore.Event.Error(
                            message = error.message ?: DEFAULT_ERROR_MESSAGE
                        )
                    )
                }
                .onSuccess { categoryList ->
                    store.dispatch(event = MainStore.Event.Success(data = categoryList))
                }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}