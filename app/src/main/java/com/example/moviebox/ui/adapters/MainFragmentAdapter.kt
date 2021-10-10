package com.example.moviebox.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebox.databinding.MainRecyclerItemBinding
import com.example.moviebox.model.entities.Movie
import com.example.moviebox.ui.main.MainFragment

class MainFragmentAdapter(private val itemViewClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var movieList: List<Movie> = listOf()
    private lateinit var binding: MainRecyclerItemBinding

    fun setMovieList(data: List<Movie>) {
        movieList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding =
            MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(binding) {
            itemMovieTitle.text = movie.title
            itemMovieYear.text = movie.year.toString()
            // загрузка картинки из Интернета по ее url
            Glide.with(root).load(movie.poster).into(itemMoviePoster)

            root.setOnClickListener { itemViewClickListener.onItemClicked(movie) }
        }
    }

}