package com.example.moviebox.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.databinding.InnerRecyclerItemBinding
import com.example.moviebox.model.rest.ApiUtils
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.ui.main.MainFragment
import com.squareup.picasso.Picasso

class InnerRecyclerAdapter(private val itemViewClickListener: MainFragment.OnItemViewClickListener) :
    ListAdapter<MovieDTO, InnerRecyclerAdapter.InnerViewHolder>(DiffCallBack()) {

    private lateinit var binding: InnerRecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        binding =
            InnerRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    // добавляем класс DiffUtil для обработки изменений в списке, и реализовываем его методы
    private class DiffCallBack : DiffUtil.ItemCallback<MovieDTO>() {
        override fun areItemsTheSame(oldItem: MovieDTO, newItem: MovieDTO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieDTO, newItem: MovieDTO) =
            oldItem == newItem
    }

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: MovieDTO) = with(binding) {
            itemMovieTitle.text = movie.title
            itemMovieYear.text = movie.releaseDate

            // загрузка картинки из Интернета по ее url
            val posterUrl = ApiUtils.POSTER_BASE_URL + ApiUtils.POSTER_SIZE_S + movie.posterPath
//            Glide.with(root).load(posterUrl).into(itemMoviePoster)
//            itemMoviePoster.load(posterUrl)
            Picasso
                .get()
                .load(posterUrl)
                .into(itemMoviePoster)

            root.setOnClickListener { itemViewClickListener.onItemClicked(movie.id) }
        }
    }

}