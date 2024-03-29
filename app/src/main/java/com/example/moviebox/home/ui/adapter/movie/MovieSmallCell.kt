package com.example.moviebox.home.ui.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.Cell
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox._core.ui.model.MovieItem
import com.example.moviebox.databinding.ItemSublistMovieBinding

class MovieSmallCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean = item is MovieItem

    override fun type(): Int = R.layout.item_sublist_movie

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSublistMovieBinding.inflate(inflater, parent, false)
        return MovieSmallViewHolder(binding = binding, listener = itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is MovieSmallViewHolder && item is MovieItem) {
            holder.bind(item = item)
        }
    }
}