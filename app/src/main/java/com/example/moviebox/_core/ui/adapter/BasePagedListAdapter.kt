package com.example.moviebox._core.ui.adapter


abstract class BasePagedListAdapter(
    vararg types: Cell<RecyclerItem>,
    private val listener: EndOfPageListener
) : BaseListAdapter(*types) {

    var loadMore: Boolean = false

    /*fun removeStatusItems() {
        submitList(currentList - ProgressItem() - ErrorItem())
    }

    fun setErrorItem() {
        if(currentList.last() is ProgressItem) {
            submitList(currentList - ProgressItem() + ErrorItem())
            loadMore = false
        }
    }

    fun setProgressItem() {
        if(currentList.last() is ErrorItem) {
            submitList(currentList - ErrorItem() + ProgressItem())
            loadMore = true
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (loadMore && listener.positionCheck(position = position, count = itemCount)) {
            submitList(currentList + ProgressItem())
            listener.loadMoreCallback()
        }
    }*/
}
