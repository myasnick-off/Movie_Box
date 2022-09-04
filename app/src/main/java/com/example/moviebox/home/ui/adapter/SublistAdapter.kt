package com.example.moviebox.home.ui.adapter

import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.BaseListAdapter
import com.example.moviebox._core.ui.adapter.cells.category.CategoryListCell

class SublistAdapter(itemClickListener: ItemClickListener) : BaseListAdapter(CategoryListCell(itemClickListener))