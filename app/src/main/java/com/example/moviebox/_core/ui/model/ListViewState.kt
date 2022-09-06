package com.example.moviebox._core.ui.model

import com.example.moviebox._core.ui.adapter.RecyclerItem

/** Класс состояний экрана со списком
 *
 * Empty - состояние отсутствуия данных (когда пришел пустой список данных)
 * Loading - состояние первоначальной загрузки
 * Refreshing - состояние обновления данных
 * MoreLoading - состояние дозагрузки данных
 * Data - состояние наличия валидных данных
 * Error - состояние ошибки
 *
 * */
sealed class ListViewState {
    object Empty : ListViewState()
    object Loading : ListViewState()
    data class Refreshing(val data: List<RecyclerItem>) : ListViewState()
    data class MoreLoading(val data: List<RecyclerItem>) : ListViewState()
    data class Data(val data: List<RecyclerItem>, val loadMore: Boolean = false) : ListViewState()
    data class Error(var message: String?) : ListViewState()
}
