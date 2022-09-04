package com.example.moviebox._core.ui.adapter.cells.category

import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox.databinding.ItemSublistBinding
import com.example.moviebox.home.ui.adapter.MoviesAdapter

class CategoryListViewHolder(
    private val binding: ItemSublistBinding,
    listener: ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private val moviesAdapter: MoviesAdapter = MoviesAdapter(listener)

    fun bind(item: CategoryListItem) = with(binding) {
        categoryTextView.text = itemView.context.getString(item.categoryResId)
        innerRecycler.adapter = moviesAdapter
        moviesAdapter.submitList(item.movies)
    }
}