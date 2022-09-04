package com.example.moviebox._core.ui.adapter.cells.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.Cell
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox.databinding.ItemSublistBinding
import com.example.moviebox.home.ui.GotoClickListener

class CategoryListCell(
    private val itemClickListener: ItemClickListener,
    private val gotoClickListener: GotoClickListener
) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean = item is CategoryListItem

    override fun type(): Int = R.layout.item_sublist

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSublistBinding.inflate(inflater, parent, false)
        return CategoryListViewHolder(
            binding = binding,
            itemClickListener = itemClickListener,
            gotoClickListener = gotoClickListener
        )
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is CategoryListViewHolder && item is CategoryListItem) {
            holder.bind(item = item)
        }
    }
}