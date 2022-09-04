package com.example.moviebox._core.ui.model

import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.adapter.cells.category.CategoryListItem

/** Класс состояний экрана
 *
 * Empty - состояние отсутствуия данных (когда пришел пустой список данных)
 * Loading - состояние первоначальной загрузки
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
    data class Data(val data: List<RecyclerItem>) : ListViewState()
    data class Error(var message: String?) : ListViewState()
}
