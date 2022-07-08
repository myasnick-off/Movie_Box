package com.example.moviebox.profile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.ApiUtils
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.OnItemViewClickListener

class TabRecyclerAdapter(private val itemViewClickListener: OnItemViewClickListener) :
    ListAdapter<MovieDTO, TabRecyclerAdapter.TabViewHolder>(DiffCallback()) {

    private class DiffCallback: DiffUtil.ItemCallback<MovieDTO>() {

        override fun areItemsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.inner_recycler_item, parent, false)
        return TabViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    inner class TabViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: MovieDTO) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.item_movie_title).text = movie.title
                itemView.findViewById<TextView>(R.id.item_movie_year).text = movie.releaseDate

                // загрузка картинки из Интернета по ее url
                val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_S + movie.posterPath
                itemView.findViewById<ImageView>(R.id.item_movie_poster).load(posterUrl)

                itemView.setOnClickListener { itemViewClickListener.onItemClicked(movie.id) }

                itemView.setOnLongClickListener {
                    itemViewClickListener.onItemLongClicked(movie, itemView)
                    true
                }
            }
        }
    }


}