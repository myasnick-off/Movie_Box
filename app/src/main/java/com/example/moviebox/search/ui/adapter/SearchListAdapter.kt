package com.example.moviebox.search.ui.adapter

import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.BasePagedListAdapter
import com.example.moviebox._core.ui.adapter.EndOfPageListener

class SearchListAdapter(
    itemClickListener: ItemClickListener,
    endOfPageListener: EndOfPageListener
) : BasePagedListAdapter(MovieLargeCell(itemClickListener), listener = endOfPageListener) {
}