package com.example.moviebox.home.ui.adapter.category

import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox.databinding.ItemSublistBinding
import com.example.moviebox.home.ui.GotoClickListener
import com.example.moviebox.home.ui.adapter.MoviesAdapter

class CategoryListViewHolder(
    private val binding: ItemSublistBinding,
    itemClickListener: ItemClickListener,
    private val gotoClickListener: GotoClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private val moviesAdapter: MoviesAdapter = MoviesAdapter(itemClickListener, gotoClickListener)

    fun bind(item: CategoryListItem) = with(binding) {
        categoryTextView.apply {
            text = itemView.context.getString(item.category.nameResId)
            setOnClickListener { gotoClickListener(categoryId = item.category.id) }
        }
        innerRecycler.adapter = moviesAdapter
        moviesAdapter.submitList(item.movies)
    }
}