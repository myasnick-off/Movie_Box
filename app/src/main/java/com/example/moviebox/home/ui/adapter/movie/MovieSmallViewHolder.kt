package com.example.moviebox.home.ui.adapter.movie

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviebox._core.data.remote.ApiUtils
import com.example.moviebox._core.ui.ItemClickListener
import com.example.moviebox._core.ui.model.MovieItem
import com.example.moviebox.databinding.ItemSublistMovieBinding

class MovieSmallViewHolder(
    private val binding: ItemSublistMovieBinding,
    private val listener: ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem) = with(binding) {
        val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_S + item.posterPath
        itemMovieTitle.text = item.title
        itemMovieYear.text = item.releaseDate
        itemMoviePoster.load(posterUrl)
        ratingLabel.text = item.voteAverage.toString()
        root.setOnClickListener { listener.onItemClicked(item.id) }
    }
}