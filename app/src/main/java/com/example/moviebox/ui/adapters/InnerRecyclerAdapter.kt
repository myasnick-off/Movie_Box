package com.example.moviebox.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebox.databinding.InnerRecyclerItemBinding
import com.example.moviebox.model.entities.Movie
import com.example.moviebox.ui.main.MainFragment

class InnerRecyclerAdapter(private val itemViewClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<InnerRecyclerAdapter.InnerViewHolder>() {

    private var movieList: List<Movie> = listOf()
    private lateinit var binding: InnerRecyclerItemBinding

    fun setData(data: List<Movie>) {
        movieList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        binding =
            InnerRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(binding) {
            itemMovieTitle.text = movie.title
            itemMovieYear.text = movie.year.toString()
            // загрузка картинки из Интернета по ее url
            Glide.with(root).load(movie.poster).into(itemMoviePoster)

            root.setOnClickListener { itemViewClickListener.onItemClicked(movie) }
        }
    }

}