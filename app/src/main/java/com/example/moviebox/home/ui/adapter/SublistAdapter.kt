package com.example.moviebox.home.ui.adapter

import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.BaseListAdapter
import com.example.moviebox.home.ui.adapter.category.CategoryListCell
import com.example.moviebox.home.ui.GotoClickListener

class SublistAdapter(itemClickListener: ItemClickListener, gotoClickListener: GotoClickListener) :
    BaseListAdapter(CategoryListCell(itemClickListener, gotoClickListener))