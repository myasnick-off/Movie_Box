package com.example.moviebox.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviebox.R
import com.example.moviebox._core.data.remote.ApiUtils
import com.example.moviebox._core.data.remote.model.MovieDTO
import com.example.moviebox._core.ui.OnItemViewClickListener

class InnerRecyclerAdapter(
    private val itemViewClickListener: OnItemViewClickListener
) :
    RecyclerView.Adapter<InnerRecyclerAdapter.InnerViewHolder>() {

    private var dataList = arrayListOf<MovieDTO>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<MovieDTO>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.inner_recycler_item, parent, false)
        return InnerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: MovieDTO) {
            itemView.findViewById<TextView>(R.id.item_movie_title).text = movie.title
            itemView.findViewById<TextView>(R.id.item_movie_year).text = movie.releaseDate

            // загрузка картинки из Интернета по ее url
            val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_S + movie.posterPath
            itemView.findViewById<ImageView>(R.id.item_movie_poster).load(posterUrl)

// Другие способы загрузки изображений
//            Glide.with(root).load(posterUrl).into(itemMoviePoster)
//            Picasso
//                .get()
//                .load(posterUrl)
//                .into(itemMoviePoster)

            itemView.setOnClickListener { itemViewClickListener.onItemClicked(movie.id) }
        }
    }

}