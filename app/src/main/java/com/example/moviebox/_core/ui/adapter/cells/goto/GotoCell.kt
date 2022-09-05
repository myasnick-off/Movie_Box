package com.example.moviebox._core.ui.adapter.cells.goto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.ui.adapter.Cell
import com.example.moviebox._core.ui.adapter.RecyclerItem
import com.example.moviebox.databinding.ItemGotoBinding
import com.example.moviebox.home.ui.GotoClickListener

class GotoCell(private val gotoClickListener: GotoClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean = item is GotoItem

    override fun type(): Int = R.layout.item_goto

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGotoBinding.inflate(inflater, parent, false)
        return GotoViewHolder(binding = binding, listener = gotoClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is GotoViewHolder && item is GotoItem) {
            holder.bind(item = item)
        }
    }
}