package com.example.moviebox._core.ui.adapter.cells.goto

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviebox._core.data.remote.ApiUtils
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox.databinding.ItemGotoBinding
import com.example.moviebox.databinding.ItemSublistMovieBinding
import com.example.moviebox.home.ui.GotoClickListener

class GotoViewHolder(
    private val binding: ItemGotoBinding,
    private val listener: GotoClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GotoItem) = with(binding) {
        root.setOnClickListener { listener(categoryId = item.categoryId) }
    }
}