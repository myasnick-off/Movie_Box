package com.example.moviebox._core.ui.adapter

class CellTypes<T>(vararg types: Cell<T>) {

    private val cellTypes: List<Cell<T>> = listOf(*types)

    fun of(item: T): Cell<T> {
        return cellTypes.first { cellType -> cellType.belongsTo(item) }
    }

    fun of(viewType: Int): Cell<T> {
        return cellTypes.first { cellType -> cellType.type() == viewType }
    }
}