package com.example.moviebox.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.Cell
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.adapter.cells.movie.MovieItem
import com.example.moviebox.databinding.ItemSearchListMovieBinding
import com.example.moviebox.search.ui.adapter.MovieLargeViewHolder
import com.example.moviebox.databinding.ItemSublistMovieBinding

class MovieLargeCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean = item is MovieItem

    override fun type(): Int = R.layout.item_search_list__movie

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchListMovieBinding.inflate(inflater, parent, false)
        return MovieLargeViewHolder(binding = binding, listener = itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is MovieLargeViewHolder && item is MovieItem) {
            holder.bind(item = item)
        }
    }
}