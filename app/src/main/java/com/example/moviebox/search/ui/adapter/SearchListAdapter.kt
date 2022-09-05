package com.example.moviebox.search.ui.adapter

import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.BasePagedListAdapter
import com.example.moviebox._core.ui.adapter.EndOfPageListener
import com.example.moviebox._core.ui.adapter.cells.movie.MovieCell

class SearchListAdapter(
    itemClickListener: ItemClickListener,
    endOfPageListener: EndOfPageListener
) : BasePagedListAdapter(MovieCell(itemClickListener), listener = endOfPageListener) {
}