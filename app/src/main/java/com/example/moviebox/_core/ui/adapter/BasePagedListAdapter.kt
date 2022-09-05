package com.example.moviebox._core.ui.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BasePagedListAdapter(
    vararg types: Cell<RecyclerItem>,
    private val listener: EndOfPageListener
) : BaseListAdapter(*types) {

    var loadMore: Boolean = false

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (loadMore && listener.positionCheck(position = position, count = itemCount)) {
            listener.loadMoreCallback()
        }
    }
}
