package com.example.moviebox.home.ui.adapter.goto

import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.databinding.ItemGotoBinding
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