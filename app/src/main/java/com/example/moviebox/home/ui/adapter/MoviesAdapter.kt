package com.example.moviebox.home.ui.adapter

import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.adapter.BaseListAdapter
import com.example.moviebox._core.ui.adapter.cells.goto.GotoCell
import com.example.moviebox._core.ui.adapter.cells.movie.MovieCell
import com.example.moviebox.home.ui.GotoClickListener

class MoviesAdapter(itemClickListener: ItemClickListener, gotoClickListener: GotoClickListener) :
    BaseListAdapter(MovieCell(itemClickListener), GotoCell(gotoClickListener))